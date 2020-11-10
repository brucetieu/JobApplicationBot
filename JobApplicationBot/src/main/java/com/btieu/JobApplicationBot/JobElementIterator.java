package com.btieu.JobApplicationBot;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;

/**
 * Iterator class to iterate through all jobs on a job page.
 * 
 * @author Bruce Tieu
 */
public class JobElementIterator implements Iterator<WebElement> {

    private List<WebElement> _list;
    private int _currIndex;

    /**
     * Initialize the current index and list of job elements.
     * 
     * @param list The list of jobs.
     */
    public JobElementIterator(List<WebElement> list) {
        _currIndex = 0;
        _list = list;
    }

    /**
     * Set the current index.
     * 
     * @param index The index.
     */
    public void setCurrIndex(int index) {
        _currIndex = index;
    }

    /**
     * Get the current index.
     * 
     * @return The current index.
     */
    public int getCurrIndex() {
        return _currIndex;
    }

    /**
     * Get the size of the list.
     * 
     * @return The size of the list.
     */
    public int getSize() {
        return _list.size();
    }

    /**
     * Check if the list can be iterated.
     */
    public boolean hasNext() {
        return _currIndex < _list.size();
    }

    /**
     * Get the next element if it exists.
     */
    public WebElement next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return _list.get(_currIndex++);

    }
    
    public void getNext() {
        _currIndex++;
    }
    
}
