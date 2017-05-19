package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.Message;

import java.util.List;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
public interface MessagesService {

    Message createMessage(String nick, String content);

    List<Message> getUsersPostedMessages(String nick);

    List<Message> getUsersTimeline(String nick);

}
