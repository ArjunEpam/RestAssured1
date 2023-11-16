package org.example;
import lombok.Data;

@Data
public class CreateIssueRequest {
    private Fields fields;

    @Data
    public static class Fields {
        private Project project;
        private String summary;
        private Description description;
        private IssueType issuetype;
        private Reporter reporter;
    }

    @Data
    public static class Project {
        private String key;
    }

    @Data
    public static class Description {
        private String type;
        private int version;
        private Content[] content;
    }

    @Data
    public static class Content {
        private String type;
        private Paragraph[] content;
    }

    @Data
    public static class Paragraph {
        private String type;
        private String text;
    }

    @Data
    public static class IssueType {
        private String name;
    }

    @Data
    public static class Reporter {
        private String id;
    }
}