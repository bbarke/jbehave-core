package org.jbehave.examples.core.stories;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.failures.RestartingStoryFailure;
import org.jbehave.core.failures.UUIDExceptionWrapper;

public class RestartingBeforeAfterScenario {
    
    private static ThreadLocal<Integer> restartCount = new ThreadLocal<Integer>();
    private static ThreadLocal<Integer> restartExampleCount = new ThreadLocal<Integer>();

    
    @AfterScenario(uponType = ScenarioType.NORMAL, uponOutcome = Outcome.FAILURE)
    public void afterNormalScenario(UUIDExceptionWrapper uuidWrappedFailure)
    {
        if(restartCount.get() == null)
        {
            restartCount.set(0);
        }
        
        //check error message
        for (Throwable cause = uuidWrappedFailure; cause != null; cause = cause.getCause()) {
            
            // Conditionally handle the failed story in the AfterScenario and decide to restart or not
            System.out.println(String.format("Cause: %s", cause.getMessage()));
            
            String msgOfCause = cause.getMessage(); 
            if (msgOfCause.contains(this.getClass().getName()) && !msgOfCause.contains("examples failure")
                    && restartCount.get() < 1) {
                System.out.printf("Throw restarting story exception, count: %s\n", restartCount.get());
                restartCount.set(restartCount.get() + 1);
                throw new RestartingStoryFailure("Restart story in After Scenario");
            }
            
        }
        
    }
    @AfterScenario(uponType = ScenarioType.EXAMPLE, uponOutcome = Outcome.FAILURE)
    public void afterExamplesScenario(UUIDExceptionWrapper uuidWrappedFailure)
    {
        if(restartExampleCount.get() == null)
        {
            restartExampleCount.set(0);
        }

        //check error message
        for (Throwable cause = uuidWrappedFailure; cause != null; cause = cause.getCause()) {
            
            // handle examples case
            String msgOfCause = cause.getMessage(); 
            if (msgOfCause.contains(this.getClass().getName()) && msgOfCause.contains("examples failure") 
                    && restartExampleCount.get() < 1) {
                System.out.printf("Throw restarting story examples exception, count: %s\n",
                        restartExampleCount.get());
                
                restartExampleCount.set(restartExampleCount.get() + 1);
                throw new RestartingStoryFailure("Restart story in After Scenario for Example");
            }
        }
    }

}
