/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kullervo16.checklist.model;

/**
 * Class to represent a state of a step or a complete checklist.
 * 
 * @author jeve
 */
public enum State implements Comparable<State>{
    UNKNOWN(0), NOT_APPLICABLE(1), OK(2), ON_HOLD(3), EXECUTION_FAILED(4), EXECUTED(5), CHECK_FAILED(6), EXECUTION_FAILED_NO_COMMENT(7), CHECK_FAILED_NO_COMMENT(8), CLOSED(9);
    
    private final int value;

    private State(int value) {
        this.value = value;
    }
    
    
}
