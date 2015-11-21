package kullervo16.checklist.model;

import java.io.File;
import java.util.List;

/**
 * Data object class to model a Checklist... it is backed by a YAML file.
 * 
 * @author jeve
 */
public class Checklist extends Template{
      
    public Checklist(File file) {
        super(file);
    }
    
   public boolean isComplete() {
       for(Step step : this.getSteps()) {
           if(!step.isComplete()) {
               // there is at least 1 not yet executed step in the list
               return false;
           }
       }
       return true;
   } 
   
   /**
    * This method calculates the worst state of the 
    * @return 
    */
   public State getState() {
       State aggregatedState = State.UNKNOWN;
       for(Step step : this.getSteps()) {
           if(step.getState().compareTo(aggregatedState) > 0) {
               aggregatedState = step.getState();
           }
       }
       return aggregatedState;
   }
   
   /**
    * This method returns a percentage of progress
    * @return 
    */
   public int getProgress() {
       List<Step> steps = this.getSteps();
       int totalSteps = steps.size();
       int stepsToDo = 0;
       for(Step step : steps) {
           if(step.getState().equals(State.UNKNOWN)) {
               stepsToDo ++;
           }
       }
       return (int)((totalSteps - stepsToDo) / (totalSteps * 0.01));
   }

}
