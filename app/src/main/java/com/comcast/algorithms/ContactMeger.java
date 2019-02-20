package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ContactMeger {

    static class Contact {
        String id;
        List<String> emails;
        Contact(String id, List<String> emails) {
            this.id = id;
            this.emails = emails;
        }
    }

    static List<HashSet<String>> findDuplicatedContacts(List<Contact> contacts) {
        HashMap<String, HashSet<String>> emailToContactsMap = new HashMap<>();
        HashMap<String, HashSet<String>> contactToEmailsMap = new HashMap<>();

        for (Contact c : contacts) {
            HashSet<String> emailSet = new HashSet<>();
            for (String email : c.emails) {
                emailSet.add(email);
                if (emailToContactsMap.containsKey(email)) {
                    emailToContactsMap.get(email).add(c.id);
                } else {
                    HashSet<String> contactSet = new HashSet<>();
                    contactSet.add(c.id);
                    emailToContactsMap.put(email, contactSet);
                }
            }
            contactToEmailsMap.put(c.id, emailSet);
        }

        HashSet<String> visited = new HashSet<>();
        List<HashSet<String>> duplicates = new ArrayList<>();

        for (String email : emailToContactsMap.keySet()) {
            if (!visited.contains(email)) {
                createDuplicate(email, emailToContactsMap, contactToEmailsMap, visited, duplicates);
            }
        }

        return duplicates;
    }

    private static void createDuplicate(String email,
                            HashMap<String, HashSet<String>> emailToContactsMap,
                            HashMap<String, HashSet<String>> contactToEmailsMap,
                            HashSet<String> visited,
                            List<HashSet<String>> duplicates) {
        visited.add(email);

        HashSet<String> relatedContacts = emailToContactsMap.get(email);
        HashSet<String> duplicate = new HashSet<>(relatedContacts);

        for (String c : relatedContacts) {
            HashSet<String> includeEmails = contactToEmailsMap.get(c);
            for (String e : includeEmails) {
                if (! visited.contains(e)) {
                    duplicate.addAll(emailToContactsMap.get(e));
                }
            }
        }

        duplicates.add(duplicate);
    }

    public static void main(String[] args) {

    }
}
