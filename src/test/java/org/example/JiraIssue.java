package org.example;

import lombok.Data;

@Data
public class JiraIssue {
    private Fields fields;

    @Data
    public static class Fields {
        private Project project;
        private String summary;
        private Description description;
        private IssueType issuetype;
        private Assignee assignee;
        private Reporter reporter;
        private String[] labels;
        private String customfield_10000;

        @Data
        public static class Project {
            private String key;
        }

        @Data
        public static class Description {
            private Doc type;
        }

        @Data
        public static class Doc {
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
            private Text[] content;
        }

        @Data
        public static class Text {
            private String type;
            private String text;
        }

        @Data
        public static class IssueType {
            private String name;
        }

        @Data
        public static class Assignee {
            private String name;
        }

        @Data
        public static class Reporter {
            private String id;
        }
    }
}
