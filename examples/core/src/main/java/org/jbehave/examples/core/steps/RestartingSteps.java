package org.jbehave.examples.core.steps;

import org.junit.Assert;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.jbehave.core.failures.RestartingScenarioFailure;
import org.jbehave.core.failures.RestartingStoryFailure;
import org.jbehave.examples.core.stories.RestartingBeforeAfterScenario;

public class RestartingSteps {
	private int restartingScenario;
	private int restartingStory;
	private static int restartScenarioInBeforeAfter = 0;
	private static int restartScenarioInExamples = 0;
	
	@When("I restart scenario")
	public void restartScenario(){
		if ( restartingScenario < 1 ){
			restartingScenario++;
			throw new RestartingScenarioFailure("Restarting scenario: "+restartingScenario);
		}
	}

	@When("I restart story")
	public void restartStory(){
		if ( restartingStory < 1 ){
			restartingStory++;
			throw new RestartingStoryFailure("Restarting story: "+restartingStory);
		}
	}
	
	@When("I restart once in after scenario")
	public void restartStoryInAfterScenario()
	{
	    if(restartScenarioInBeforeAfter < 1)
	    {
	        restartScenarioInBeforeAfter++;
            throw new IllegalArgumentException(
                    "Hey, " + RestartingBeforeAfterScenario.class.getName() + ", handle this failure");
	    }
	}
	
    @When("I restart once in after scenario using example <Test>")
    public void restartUsingExamples(@Named("Test")int example)
    {
        System.out.printf("Example: %s\n", example, restartScenarioInExamples);
        
        restartScenarioInExamples++;
        switch (example) {
        case 1: // do nothing
            break;
        case 2:
            if (restartScenarioInExamples == 2) {
                throw new IllegalArgumentException(String.format(
                    "Hey, %s, handle this examples failure %s",
                    RestartingBeforeAfterScenario.class.getName(), example));
            }
            break;
        case 3:
            Assert.assertEquals(
                "Examples scenarios continued to run after throwing an exception for restarting story",
                5, restartScenarioInExamples);
            break;

        default:
            throw new IllegalArgumentException(
                String.format("Not expecting example #%s in restarting step examples!", example));
        }

	}
}

