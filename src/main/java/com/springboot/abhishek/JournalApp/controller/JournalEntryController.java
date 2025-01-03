//package com.springboot.abhishek.JournalApp.controller;
//
//import com.springboot.abhishek.JournalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//
//    private Map<String, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());    //Get
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry myEntry){
//        journalEntries.put(myEntry.getId(), myEntry);
//        return true;
//    }
//
//    @GetMapping("/id/{myId}")
//    public JournalEntry getJournalEntryById(@PathVariable String  myId){
//        return journalEntries.get(myId);
//    }
//
//    @DeleteMapping("/id/{myId}")
//    public JournalEntry deleteJournalEntry(@PathVariable String myId){
//        return journalEntries.remove(myId);
//    }
//
//    @PutMapping("/id/{myId}")
//    public JournalEntry updateJournalById(@PathVariable String myId, @RequestBody JournalEntry myEntry){
//        return journalEntries.put(myId,myEntry);
//    }
//}
