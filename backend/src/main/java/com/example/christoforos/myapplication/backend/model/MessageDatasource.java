package com.example.christoforos.myapplication.backend.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by christoforos on 12/12/2016.
 */

public class MessageDatasource {

    public static final String KIND_MESSAGE = "kind_message";
    public static final String PROPERTY_TEXT = "property_message";
    public static final Logger logger = Logger.getLogger(MessageDatasource.class.getName());

    public String saveMessageToDatastore(Message message, DatastoreService datastoreService) {
        Entity messageEntity = new Entity(KIND_MESSAGE);
        messageEntity.setProperty(PROPERTY_TEXT, message.getText());
        logger.severe("The following message is saved: " + message.getText());
        datastoreService.put(messageEntity);
        return KeyFactory.keyToString(messageEntity.getKey());
    }

    public List<Message> getAllMessages(DatastoreService datastoreService) {
        List<Entity> msgEntities = getAllMessageEntities(datastoreService);
        MessageMapper mapper = new MessageMapper();
        List<Message> messages = new ArrayList<>(msgEntities.size());
        for (Entity entity :
                msgEntities) {
            Message message = mapper.fromEntity(entity);
            messages.add(message);
        }
        return messages;
    }

    public List<Message> searchMessage(String text, DatastoreService datastoreService) {
        List<Entity> msgEntities = getAllMessageEntities(datastoreService);
        MessageMapper mapper = new MessageMapper();
        List<Message> messages = new ArrayList<>(msgEntities.size());
        for (Entity entity :
                msgEntities) {
            Message message = mapper.fromEntity(entity);
            if(message.getText() != null && message.getText().contains(text)) {
                messages.add(message);
            }
        }
        return messages;
    }

    public List<Message> searchExactMessage(String text, DatastoreService datastoreService) {
        List<Entity> msgEntities = getMessageEntities(text, datastoreService);
        MessageMapper mapper = new MessageMapper();
        List<Message> messages = new ArrayList<>(msgEntities.size());
        for (Entity entity :
                msgEntities) {
            Message message = mapper.fromEntity(entity);
            messages.add(message);
        }
        return messages;
    }

    public void deleteMessages(List<Message> messages, DatastoreService datastoreService) {
        for (Message message :
                messages) {
            datastoreService.delete(KeyFactory.stringToKey(message.getId()));
        }
    }

    private List<Entity> getMessageEntities(String text, DatastoreService datastoreService) {
        Query.Filter propertyFilter =
                new Query.FilterPredicate(PROPERTY_TEXT, Query.FilterOperator.EQUAL, text);
        Query query = new Query(KIND_MESSAGE).setFilter(propertyFilter);
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        FetchOptions options = FetchOptions.Builder.withDefaults();
        return preparedQuery.asList(options);
    }

    private List<Entity> getAllMessageEntities(DatastoreService datastoreService) {
        Query query = new Query(KIND_MESSAGE);
        PreparedQuery preparedQuery = datastoreService.prepare(query);
        FetchOptions options = FetchOptions.Builder.withDefaults();
        return preparedQuery.asList(options);
    }

}