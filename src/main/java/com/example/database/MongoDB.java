package com.example.database;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.*;

public class MongoDB {
    private static final String connectionString = "mongodb://localhost:27017";
    private static final String databaseName = "School";
    private static final ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    private static final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    public enum Collections {
        Children, Employees,TimeTable
    }

    ///////////////////////// \ CRUD / /////////////////////////
    /**
     * @param collection the collection to insert the document in
     * @param document   the document to insert
     *
     */
    protected static void createDocument(Collections collection, Document document) {
        // Create a new client and connect to the server (after this try block ends, the
        // connection will automatically end)
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> mongoCollection = database.getCollection(collection.toString());
            mongoCollection.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param collection the collection to read the documents from
     * @param filters    the filters to apply to the documents
     */
    protected static List<Document> readDocuments(Collections collection, Bson filters) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> mongoCollection = database.getCollection(collection.toString());
            ArrayList<Document> list = new ArrayList<>();
            if (filters == null) {
                mongoCollection.find().forEach(list::add);
                return List.copyOf(list);
            } else {
                mongoCollection.find(filters).forEach(list::add);
                return List.copyOf(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param collection the collection to update the documents in
     * @param filters    the filters to apply to the documents
     */
    protected static void updateDocument(Collections collection, Bson filters, Bson update) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> mongoCollection = database.getCollection(collection.toString());
            mongoCollection.updateOne(filters, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param collection the collection to delete the documents from
     * @param filters    the filters to apply to the documents
     */
    protected static void deleteDocument(Collections collection, Bson filters) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> mongoCollection = database.getCollection(collection.toString());
            mongoCollection.deleteOne(filters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void dropCollection(Collections collection) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.getCollection(collection.toString()).drop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////

}
