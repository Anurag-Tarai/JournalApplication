package com.anurag.journalapp.scheduler;

import com.anurag.journalapp.entity.Journal;
import com.anurag.journalapp.entity.User;
import com.anurag.journalapp.enums.Sentiment;
import com.anurag.journalapp.repository.JournalEntryRepo;
import com.anurag.journalapp.repository.UserRepositoryImp;
import com.anurag.journalapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepositoryImp userRepositoryImp;

    @Autowired
    JournalEntryRepo journalEntryRepo;

    @Scheduled(cron = "0 * * * * *")
//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAmail(){
        List<User> users = userRepositoryImp.getUsersForSA();
        for(User user: users){
            // fetch all non-archived journals
            Page<Journal> page = journalEntryRepo.findByUserIdAndArchivedFalse(user.getId(), Pageable.unpaged());

            Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);

            List<Sentiment> sentiments = page.getContent().stream()
                    .filter(j -> j.getCreatedAt() != null && j.getCreatedAt().isAfter(sevenDaysAgo))
                    .map(Journal::getSentiment)
                    .collect(Collectors.toList());

            // find most occurred sentiment
            Sentiment mostOccurred = sentiments.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            emailService.sendMail(user.getEmail(), user.getFirstName() + " your mood of this week is: ", String.valueOf(mostOccurred));
            System.out.println("User " + user.getFirstName() + " most common sentiment: " + mostOccurred);
        }
    }

}
