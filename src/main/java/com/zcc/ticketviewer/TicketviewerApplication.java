package com.zcc.ticketviewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.dto.GetTicketByIdResponse;
import com.zcc.ticketviewer.dto.GetTicketsResponse;
import com.zcc.ticketviewer.dto.Tickets;
import com.zcc.ticketviewer.exception.ApiException;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
@SpringBootApplication
public class TicketviewerApplication implements CommandLineRunner {
	@Autowired
	private TicketService ticketService;

	@Value("${zcc.url}")
	private String host;

	@Autowired
	ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(TicketviewerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.error("executing command line interface " );
		String url = host + "/api/v2/tickets.json?page[size]=25";
		String requestUrl = host + "/api/v2/tickets/";

		final Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the Ticket Viewer! Please enter the following details to proceed");
		System.out.println("Your Account ID: !");
		String accountId = in.nextLine();
		System.out.println("Your password: ");
		String password = in.nextLine();
		Secrets secret = new Secrets(accountId, password);
		while(true){
			System.out.println("Please enter from the following:\n" +
					"1 to retrieve all tickets\n" +
					"2 to retrieve ticket by ID\n" +
					"3 to enter account ID and password again\n" +
					"4 to leave the TicketViewer");
			Integer input = Integer.parseInt(in.nextLine());
			GetTicketsResponse response = null;
			try{
				if(input == 4){
					break;
				}else if(input == 3){
					System.out.println("Your Account ID: !");
					accountId = in.nextLine();
					System.out.println("Your password: ");
					password = in.nextLine();
					secret = new Secrets(accountId, password);
				} else if(input == 2){
					System.out.println("Please enter the ticket id: ");
					int id = Integer.parseInt(in.nextLine());
					response = convertToTicketsResponse(ticketService.getTicketById(requestUrl, id, secret));
				} else {
					response = ticketService.getTickets(url, secret);
				}
			}catch(ApiException ex){
				System.out.println(ex.getMessage());
			}

			boolean exitViewer = false;
			try{
				while(true){
					if(response == null ){
						System.out.println("No ticket found!");
						break;
					}
					if(response.getTickets().size() == 0){
						System.out.println("No Tickets found!");
						break;
					}
					for(int i=0;i<response.getTickets().size();i++){
						System.out.println("Ticket Details of ticket with id: " + response.getTickets().get(i).getId());
						System.out.println("title: " + response.getTickets().get(i).getSubject() );
						System.out.println("created at: " + response.getTickets().get(i).getCreatedAt());
						System.out.println("requested by: " + response.getTickets().get(i).getRequestedId());
						System.out.println("status: " + response.getTickets().get(i).getStatus() );
						if(response.getTickets().get(i).getDescription() != null )
							System.out.println("description: " + response.getTickets().get(i).getDescription() );
						System.out.println("url: " + response.getTickets().get(i).getUrl() );
						System.out.println("\n\n");
					}

					if(response.getMeta()!=null && response.getMeta().getHasMore()!= null && response.getMeta().getHasMore()){
						System.out.println("Please enter from the following: \n2 to move to next page of the tickets\n3 to view the previous 25 tickets\n4 to retrieve the tickets from start\n5 to exit the ticket viewer");
						int x = Integer.parseInt(in.nextLine());
						if(x == 2){
							response = ticketService.getTickets(response.getLinks().getNext(), secret);
						} else if(x == 3){
							response = ticketService.getTickets(response.getLinks().getPrev(), secret);
						} else if(x == 5){
							exitViewer = true;
							break;
						} else{
							break;
						}
					} else{
						System.out.println("All tickets retrieved, please enter from the following: \n4 to return to the main menu\n5 to exit the ticket viewer");
						int x = Integer.parseInt(in.nextLine());
						if(x == 3){
							response = ticketService.getTickets(response.getLinks().getPrev(), secret);
						} else if(x == 5){
							exitViewer = true;
							break;
						} else{
							break;
						}
					}
				}
			}catch(ApiException ex){
				System.out.println(ex.getMessage());
			}

			if(exitViewer){
				break;
			}

		}
		System.out.println("Thanks for using TicketViewer");
		URL shut = new URL("http://127.0.0.1:8080/actuator/shutdown");
		HttpURLConnection con = (HttpURLConnection) shut.openConnection();
		con.setRequestMethod("POST");
		con.connect();
		log.info("Exiting the system status: " + con.getResponseCode());


	}

	private GetTicketsResponse convertToTicketsResponse(GetTicketByIdResponse request) {
		try {
			System.out.println(objectMapper.writeValueAsString(request));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		GetTicketsResponse getTicketsResponse = new GetTicketsResponse();
		List<Tickets> tickets = new ArrayList<>();
		if(request == null){
			return null;
		}
		if(request.getTicket() != null){
			tickets.add(request.getTicket());
		}
		getTicketsResponse.setTickets(tickets);
		return getTicketsResponse;
	}
}
