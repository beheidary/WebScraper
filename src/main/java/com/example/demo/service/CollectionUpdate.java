package com.example.demo.service;


import com.example.demo.entity.DoctorK;
import com.example.demo.jpa.DoctorKRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;


public class CollectionUpdate {

        private static JsonNode jsonNode;

        private static DoctorKRepository doctorKRepository;
        static ObjectMapper mapper = new ObjectMapper();
        static ObjectReader reader = mapper.reader(JsonNode.class);

        public static void main(String[] args) {

            String connectionString = "mongodb://admin:admin@localhost:27017/admin?authMechanism=SCRAM-SHA-256";
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .build();
            try (var mongoClient = MongoClients.create(settings)) {
                MongoDatabase database = mongoClient.getDatabase("test");

                MongoCollection<Document> collectionDoctorK = database.getCollection("DoctorK");
                MongoCollection<Document> collectionboghratDoctors = database.getCollection("boghratDoctors");



                FindIterable<Document> DoctorKdocuments = collectionDoctorK.find();
                DoctorK doctorKDocument  = doctorKRepository.findBynezam("454545");
                System.out.println(doctorKDocument);
                FindIterable<Document> boghratDoctorsdocuments = collectionboghratDoctors.find();


                int counter = 1;
                try (MongoCursor<Document> cursor = boghratDoctorsdocuments.iterator()) {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();

                        JsonNode node = reader.readValue(document.toJson());

                            ObjectNode jsonPartNode = mapper.createObjectNode();
                            jsonPartNode.put("id",counter);
                            String fullName = node.get("fullName").asText();
                            jsonPartNode.put("full_name",fullName.substring(5) );
                            jsonPartNode.put("active_count", 1);
                            jsonPartNode.put("sites", "doctoreto_");
                            jsonPartNode.put("nezam_code", node.get("medicalNumber"));

                            String jsonPart2 = jsonPartNode.toString();

                            database.getCollection("DoctorK").insertOne(Document.parse(jsonPart2));


                        counter++;
                    }
                }
//
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


//copy collection
//
//        db.DoctorK.find().forEach(
//                function(x){
//                db.CopyDoctorK.insert(x)
//                }
//                )

