package com.springboot.abhishek.JournalApp.service;

import com.springboot.abhishek.JournalApp.entity.JournalEntry;
import com.springboot.abhishek.JournalApp.entity.User;
import com.springboot.abhishek.JournalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional      // this annotation will consider below as one operation and rollback if any one operation fails
                        // example and use case of this is setting username null
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(journalEntry);

            // below line set the user name as null which will throw exception at runtime and we want rollback functionality
            // on journalentry stored data
            user.setUserName(null);
            userService.saveUser(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while  saving....",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        try {
            journalEntryRepository.save(journalEntry);
        }catch (Exception e){
            log.error("Exception", e);
        }

    }

    public List<JournalEntry> getALL() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}


// controller --> service --> repository