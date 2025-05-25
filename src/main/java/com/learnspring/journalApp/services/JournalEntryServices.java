package com.learnspring.journalApp.services;

import com.learnspring.journalApp.entity.JournalEntry;
import com.learnspring.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);

    }
    public List<JournalEntry> getAllEntries(){
       return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getJournalById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
