package yash.SpringProject.JournalApp.repositiory;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import yash.SpringProject.JournalApp.Entity.JournalEntry;
import yash.SpringProject.JournalApp.Entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);
}
