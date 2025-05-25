package com.learnspring.journalApp.controller;

import com.learnspring.journalApp.entity.JournalEntry;
import com.learnspring.journalApp.services.JournalEntryServices;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @PostMapping("/create")
    public ResponseEntity<String> saveEntry(@NotNull @RequestBody JournalEntry journalEntry){
        journalEntryServices.saveEntry(journalEntry);
        return new ResponseEntity<>("Journal Entry created successfully",HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> entries = journalEntryServices.getAllEntries();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id) {
        Optional<JournalEntry> entry = journalEntryServices.getJournalById(id);
        if (entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/id/{id}")
    public ResponseEntity<String> UpdateEntry(@NotNull @RequestBody JournalEntry newEntry, @PathVariable ObjectId id){
        Optional<JournalEntry> existingOpt = journalEntryServices.getJournalById(id);
        if (existingOpt.isPresent()) {
            JournalEntry existing = existingOpt.get();
            existing.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : existing.getTitle());
            existing.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : existing.getContent());

            journalEntryServices.saveEntry(existing);
            return new ResponseEntity<>("Journal entry updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteJournalEntry(@PathVariable ObjectId id){
        Optional<JournalEntry> existingOpt = journalEntryServices.getJournalById(id);
        if (existingOpt.isPresent()) {
            journalEntryServices.deleteEntry(id);
            return new ResponseEntity<>("Journal entry deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
        }
    }

}
