package com.example.demo.jpa;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;


    public class CollectionUpdate {

        private static JsonNode jsonNode;
        static ObjectMapper mapper = new ObjectMapper();
        static ObjectReader reader = mapper.reader(JsonNode.class);

        public static void main(String[] args) {

            String connectionString = "mongodb://admin:admin@localhost:27017/admin?authMechanism=SCRAM-SHA-256";
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .build();
            try (var mongoClient = MongoClients.create(settings)) {
                MongoDatabase database = mongoClient.getDatabase("test");

                MongoCollection<Document> collection = database.getCollection("doctorsByCity");


                FindIterable<Document> documents = collection.find();

                int counter = 1;
                try (MongoCursor<Document> cursor = documents.iterator()) {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();

                        JsonNode node = reader.readValue(document.toJson());
                        for (JsonNode data : node.get("Data").get("data")){
                            ObjectNode jsonPartNode = mapper.createObjectNode();
                            jsonPartNode.put("Id" , counter );
                            jsonPartNode.put("Date", node.get("Date"));
                            jsonPartNode.put("SiteName", node.get("SIteName"));

                            jsonPartNode.setAll((ObjectNode) data);

                            String jsonPart2 = jsonPartNode.toString();



                            database.getCollection("DoctoreNext").insertOne(Document.parse(jsonPart2));


                        }



                        counter++;
                    }
                }

                System.out.println("Field added successfully to all documents.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


//
//        use test
//
//// Update documents in your collection use database console
//db.DoctoreTo.updateMany(
//        {},
//        [
//        {
//        $set: {
//        Id: {
//        $toInt: "$Id"
//        }
//        }
//        }
//        ],
//        {
//        }
//        );


//        use test
//
//// Merge documents from collection2 into collection1
//db.DoctoreToSec2.aggregate([
//        {
//        $merge: {
//        into: "DoctoreTo",
//        whenMatched: "merge",
//        whenNotMatched: "insert"
//        }
//        }
//        ]);




//remove duplicated records
//
//
//        use test
//        db.DoctoreNext.aggregate([
//        {
//        $group: {
//        _id: { id: "$id" },  // Group by the field you want to check for duplicates
//        uniqueIds: { $addToSet: "$_id" },  // Create an array of unique document ids
//        count: { $sum: 1 }  // Count occurrences of each value
//        }
//        },
//        {
//        $match: {
//        count: { $gt: 1 }  // Filter only those values that appear more than once (duplicates)
//        }
//        }
//        ]).forEach(function(doc) {
//        // Remove duplicates by keeping only the first occurrence
//        doc.uniqueIds.shift();
//        // Delete the remaining duplicate documents
//        db.DoctoreNext.deleteMany({ _id: { $in: doc.uniqueIds } });
//        });
