package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

public class JiraService {
    private static final String JIRA_API_URL = "https://arjunan.atlassian.net/rest/api/3/issue";
    private static final String USERNAME = "arjunganesan12@gmail.com";
    private static final String API_TOKEN = "ATATT3xFfGF0vc8ZmF0YDtwmPJkua2weuZAQ7meWJ-uknkubvN3grbHwTqVuri2f6PakwazKgty-bnFITCmeTcOgTGl0BUnUlqwDKlNfcyXetCw7JdVYfS0CsOPn6Sq7ggx1c_tAViq7Fl8PHtXulVhpu9-lYK48yhL8TUZpOl6epmvk3LdNU6Y=270D29A0";
    private static String filePath = "C:\\Users\\arjun_g\\Downloads\\projects\\commandlineProject\\RestAssured\\src\\test\\java\\org\\example\\weather.png";

    public static Response createJiraIssue(CreateIssueRequest issue) {
        return RestAssured.given()
                .auth().preemptive().basic(USERNAME, API_TOKEN)
                .log().all()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(issue)
                .post(JIRA_API_URL);
    }

    public static Response getJiraIssue(String idValue) {
        return RestAssured.given()
                .auth().preemptive().basic(USERNAME, API_TOKEN)
                .pathParam("idValue", idValue)
                .log().all()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")

                .get(JIRA_API_URL+"/{idValue}");
    }

    public static Response addAttachmentJiraIssue(String idValue) {
        return RestAssured.given()
                .multiPart(new File(filePath))
                .auth().preemptive().basic(USERNAME, API_TOKEN)
                .pathParam("idValue", idValue)
                .log().all()
                .header("Content-Type", "multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
               /* .header("Accept", "application/json")
                .header("Content-Type", "application/json")*/

                .post(JIRA_API_URL+"/{idValue}/attachments");
    }

    public static Response deleteJiraIssue(String idValue) {
        return RestAssured.given()
                .auth().preemptive().basic(USERNAME, API_TOKEN)
                .pathParam("idValue", idValue)
                .log().all()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .delete(JIRA_API_URL+"/{idValue}");
    }
}
