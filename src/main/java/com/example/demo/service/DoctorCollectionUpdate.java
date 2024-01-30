package com.example.demo.service;

        import com.example.demo.entity.DoctorK;
        import com.example.demo.jpa.DoctorKRepository;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.ObjectReader;
        import com.mongodb.ConnectionString;
        import com.mongodb.MongoClientSettings;
        import com.mongodb.client.*;
        import lombok.RequiredArgsConstructor;
        import org.bson.Document;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;
        import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorCollectionUpdate {






    private final DoctorKRepository doctorKRepository;
    private final ObjectMapper objectMapper;





    private final ObjectMapper mapper;

    public String DoctorkUpdate() throws JsonProcessingException, InterruptedException {

        ObjectReader reader = objectMapper.reader(JsonNode.class);

        String connectionString = "mongodb://admin:admin@localhost:27017/admin?authMechanism=SCRAM-SHA-256";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collectiondoctorsByExpertise = database.getCollection("SnapDoctorFullDetail");


        FindIterable<Document> doctorsByExpertisedocuments = collectiondoctorsByExpertise.find();
        List<DoctorK> doctorKS = doctorKRepository.findAllBynezamNotNull();
        ArrayList<String> nezams = new ArrayList<>();
        for (DoctorK doctorK : doctorKS){

            nezams.add(doctorK.getNezam());

        }



        int counter = 200411;



                try (MongoCursor<Document> cursor = doctorsByExpertisedocuments.iterator()) {
                    while (cursor.hasNext()) {
                        Document document = cursor.next();

                        JsonNode node = reader.readValue(document.toJson());


                            String Tempnezam_code = node.get("full_description").asText();
                            int len = node.get("full_description").asText().length();
                            int index = Tempnezam_code.indexOf(":");

                            if(len<index+8){
                                Tempnezam_code = Tempnezam_code.substring(index+1,len).trim();
                            }else {
                                Tempnezam_code = Tempnezam_code.substring(index+1,index+8).trim();
                            }



                            if (nezams.contains(Tempnezam_code)){
                                DoctorK doctorKDocument = doctorKRepository.findBynezam(Tempnezam_code);


                                if(doctorKDocument.getSites().contains("snappdoctor_")) {

                                }else {
                                    doctorKDocument.setActive_count(doctorKDocument.getActive_count() + 1);
                                    doctorKDocument.setSites(doctorKDocument.getSites() + "snappdoctor_");
                                    doctorKRepository.save(doctorKDocument);
                                }



                            }else {
                                String trash = "دکتر";
                                DoctorK doctorK = new DoctorK();
                                doctorK.setActive_count(1);
                                doctorK.setNezam(Tempnezam_code);
                                doctorK.setFull_name(node.get("name").asText().replace(trash,"").trim());
                                doctorK.setSites("snappdoctor_");
                                doctorK.setIndex(counter);
                                doctorKRepository.save(doctorK);
                                counter++;

                            }



                    }
                }


        System.out.println("Field added successfully to all documents.");


        return "ok";


    }
}

