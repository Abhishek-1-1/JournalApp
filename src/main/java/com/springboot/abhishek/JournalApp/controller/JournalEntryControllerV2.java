package com.springboot.abhishek.JournalApp.controller;

import com.springboot.abhishek.JournalApp.entity.JournalEntry;
import com.springboot.abhishek.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

   @Autowired
   private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getALL();
        if(all != null && !all.isEmpty()){
            for (int i = 0; i < all.size(); i++) {
                System.out.println("for getting id" + all.get(i));
            }
            return new ResponseEntity<>(all,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

        try {
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
         journalEntryService.deleteEntryById(myId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable ObjectId id){
        JournalEntry oldJournalEntry = journalEntryService.findById(id).orElse(null);
        if (oldJournalEntry != null){
            oldJournalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldJournalEntry.getContent());
            journalEntryService.saveEntry(oldJournalEntry);
            return new ResponseEntity<>(oldJournalEntry,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
