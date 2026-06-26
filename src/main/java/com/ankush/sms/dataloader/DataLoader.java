package com.ankush.sms.dataloader;


import net.datafaker.Faker;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DataLoader {

    private static final String API_URL =
            "http://localhost:8080/api/admin/students";

    private static final String TOKEN =
            "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzgyNTAyNDUwLCJleHAiOjE3ODI1ODg4NTB9.UShxAgU3oijsIlx8b1ukEYZfo7QzIs0FDzaKZvdkEwyd6onB1Ib_wMLZnDPAwl_vJeWOp7-dejrBWc2MjEnJ9Q";

    private static final Faker faker = new Faker(new Locale("en", "IN"));

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        for (int i = 1; i <= 1000; i++) {

            try {

                Map<String, Object> student = new LinkedHashMap<>();

                student.put("name", faker.name().fullName());

                LocalDate dob = LocalDate.now()
                        .minusYears(18 + faker.number().numberBetween(0, 7))
                        .minusDays(faker.number().numberBetween(1, 365));

                student.put("dob", dob.toString());

                student.put("gender",
                        faker.bool().bool() ? "MALE" : "FEMALE");

                student.put("studentCode",
                        String.format("STU%05d", i));

                student.put("email",
                        "student" + i + "@gmail.com");

                student.put("mobile",
                        "9" + faker.number().digits(9));

                student.put("parentName",
                        faker.name().fullName());

                List<Map<String, String>> addresses = new ArrayList<>();

                Map<String, String> permanent = new LinkedHashMap<>();
                permanent.put("type", "PERMANENT");
                permanent.put("street", faker.address().streetAddress());
                permanent.put("city", faker.address().city());
                permanent.put("state", faker.address().state());
                permanent.put("country", "India");
                permanent.put("pincode", faker.address().zipCode());

                Map<String, String> current = new LinkedHashMap<>();
                current.put("type", "CURRENT");
                current.put("street", faker.address().streetAddress());
                current.put("city", faker.address().city());
                current.put("state", faker.address().state());
                current.put("country", "India");
                current.put("pincode", faker.address().zipCode());

                addresses.add(permanent);
                addresses.add(current);

                student.put("addresses", addresses);

                String json = mapper.writeValueAsString(student);

                URL url = new URL(API_URL);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty(
                        "Authorization",
                        "Bearer " + TOKEN);

                connection.setRequestProperty(
                        "Content-Type",
                        "application/json");

                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(json.getBytes());
                }

                int code = connection.getResponseCode();

                if (code == 200 || code == 201) {
                    System.out.println("Inserted Student " + i);
                } else {
                    System.out.println("Failed Student "
                            + i + " -> " + code);
                }

                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished inserting 1000 students.");
    }
}
