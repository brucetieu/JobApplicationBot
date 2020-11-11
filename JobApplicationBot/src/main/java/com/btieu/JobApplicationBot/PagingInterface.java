package com.btieu.JobApplicationBot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface PagingInterface {
    /**
     * Abstract method that is implemented by a lamda expression.
     * 
     * @param pageNum The page number to search for jobs.
     * @return A list of jobs of type WebElement.
     */
    public List<WebElement> handlePage(int pageNum);
}

