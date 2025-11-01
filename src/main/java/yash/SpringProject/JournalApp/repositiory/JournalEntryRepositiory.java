package yash.SpringProject.JournalApp.repositiory;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import yash.SpringProject.JournalApp.Entity.JournalEntry;

public interface JournalEntryRepositiory extends MongoRepository<JournalEntry, ObjectId> {


}
