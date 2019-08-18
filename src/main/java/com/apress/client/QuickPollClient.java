package com.apress.client;

import org.springframework.web.client.RestTemplate;

import com.apress.domain.Option;
import com.apress.domain.Poll;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

public class QuickPollClient {

	private static final String QUICK_POLL_URI_V1 = "http://localhost:8080/v1/polls";
	private RestTemplate restTemplate = new RestTemplate();

	public Poll getPollById(Long pollId) {
		return restTemplate.getForObject(QUICK_POLL_URI_V1 + "/{pollId}", Poll.class, pollId);
	}

	public List<Poll> getAllPolls() {
		ParameterizedTypeReference<List<Poll>> responseType = new ParameterizedTypeReference
		<List<Poll>>() {};
		ResponseEntity<List<Poll>> responseEntity = restTemplate.exchange(QUICK_POLL_URI_V1,
		HttpMethod.GET, null, responseType);
		List<Poll> allPolls = responseEntity.getBody();
		return allPolls;
		}
	
	public URI createPoll(Poll poll) {
		return restTemplate.postForLocation( QUICK_POLL_URI_V1, poll);
		}
	
	public void updatePoll(Poll poll) {
		restTemplate.put(QUICK_POLL_URI_V1 + "/{pollId}", poll, poll.getId());
		}
	
	public void deletePoll(Long pollId) {
		restTemplate.delete(QUICK_POLL_URI_V1 + "/{pollId}", pollId);
		}
	
	public static void main(String[] args) {
		QuickPollClient client = new QuickPollClient();
		
		Poll poll = client.getPollById(1L);
		System.out.println(poll);
		
		Poll newPoll = new Poll();
		newPoll.setQuestion("What is your favourate color?");
		Set<Option> options = new HashSet<>();
		newPoll.setOptions(options);
		Option option1 = new Option(); option1.setValue("Red"); options.add(option1);
		Option option2 = new Option(); option2.setValue("Blue");options.add(option2);
		URI pollLocation = client.createPoll(newPoll);
		System.out.println("Newly Created Poll Location " + pollLocation);
	}
	

}
