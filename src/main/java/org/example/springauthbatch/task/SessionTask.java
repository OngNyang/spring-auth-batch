package org.example.springauthbatch.task;

import org.example.springauthbatch.entity.UserSession;
import org.example.springauthbatch.repository.UserSessionRepository;
import org.example.springauthbatch.service.RedisSessionService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SessionTask implements Tasklet {
    private final RedisSessionService   redisSessionService;
    private final UserSessionRepository userSessionRepository;

    public SessionTask(RedisSessionService redisSessionService, UserSessionRepository userSessionRepository) {
        this.redisSessionService = redisSessionService;
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Set<String> activeSessions = redisSessionService.getActiveSessions();
        System.out.println("job executed....");
        System.out.print("activeSessions: ");
        System.out.println(activeSessions);

        for (String sessionId : activeSessions) {
            saveRedisSessionToDb(sessionId);
        }

        return (RepeatStatus.FINISHED);
    }

    public void saveRedisSessionToDb(String sessionId) {
        Object              sessionDetails = redisSessionService.getSessionDetails(sessionId);
        Map<String, Object> sessionData;
        UserSession         userSession;

        sessionData = (Map<String, Object>) sessionDetails;
        userSession = mapToEntity(sessionId, sessionData);
        userSessionRepository.save(userSession);
        System.out.print("sessionData: ");
        System.out.print(sessionData);
        System.out.print("userSession: ");
        System.out.print(userSession);
    }

    private UserSession mapToEntity(String sessionId, Map<String, Object> sessionData) {
        UserSession userSession = UserSession.builder()
                .sessionId(sessionId)
                .username((String) sessionData.get("username"))
                .roles(String.join(",", (List<String>) sessionData.get("roles")))
                .build();

        return (userSession);
    }
}
