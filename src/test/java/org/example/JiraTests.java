package org.example;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JiraTests {
    Response response;
    private CreateIssueRequest requestss;
    String si;
    String idValue;

    @Test(priority = 1)
    public void jiraTestClass() {
        CreateIssueRequest createIssueRequest = new CreateIssueRequest();

        CreateIssueRequest.Fields fields = new CreateIssueRequest.Fields();
        fields.setProject(new CreateIssueRequest.Project());
        fields.getProject().setKey("KAN");
        fields.setSummary("Create a new feature");

        CreateIssueRequest.Description description = new CreateIssueRequest.Description();
        description.setType("doc");
        description.setVersion(1);

        CreateIssueRequest.Content content = new CreateIssueRequest.Content();
        content.setType("paragraph");

        CreateIssueRequest.Paragraph paragraph = new CreateIssueRequest.Paragraph();
        paragraph.setType("text");
        paragraph.setText("description");

        content.setContent(new CreateIssueRequest.Paragraph[]{paragraph});

        description.setContent(new CreateIssueRequest.Content[]{content});
        fields.setDescription(description);

        fields.setIssuetype(new CreateIssueRequest.IssueType());
        fields.getIssuetype().setName("Task");

        fields.setReporter(new CreateIssueRequest.Reporter());
        fields.getReporter().setId("712020:846b6cc7-2f36-4d7c-917f-226919d1b2bf");

        createIssueRequest.setFields(fields);

        si = JiraService.createJiraIssue(createIssueRequest).asString();
        JsonPath jsonPath = new JsonPath(si);
        idValue = jsonPath.get("id");
        Assert.assertNotNull(jsonPath.get("id"));
    }

    @Test(priority = 2, dependsOnMethods = {"jiraTestClass"})
    public void jiraGetDetails() {
        response = JiraService.getJiraIssue(idValue);
        response.then().log().all();
        si = response.asString();
        JsonPath jsonPath = new JsonPath(si);
        idValue = jsonPath.get("id");
        Assert.assertNotNull(jsonPath.get("id"));
    }

    @Test(priority = 3, dependsOnMethods = {"jiraGetDetails"})
    public void addAttachments() {
        response = JiraService.addAttachmentJiraIssue(idValue);
        response.then().log().all();
        si = response.asString();
        JsonPath jsonPath = new JsonPath(si);
        Assert.assertNotNull(jsonPath.get("[0].id"));
    }

    @Test(priority = 4)
    public void deleteAttachments() {
        response = JiraService.deleteJiraIssue(idValue);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 204);
    }
}
