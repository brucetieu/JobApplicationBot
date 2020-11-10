package com.btieu.JobApplicationBot;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.btieu.JobApplicationBot.JobApplicationData.ApplicationType;

public interface ApplyInterface {
    public void myMethod(int index, List<WebElement> jobList);
}

class EasyAndNotEasyApply {
    private JobApplicationData _jobAppData;
    private JobApplicationData.ApplicationType _appType;
    private IndeedBot _indeedBot;
    private Bot _bot;

    public EasyAndNotEasyApply(JobApplicationData jobAppData, ApplicationType appType) {
        _jobAppData = jobAppData;
        _appType = appType;
        _indeedBot = new IndeedBot();
        _bot = new Bot();
    }

    public void findAndSaveEasyApplyJob(int index, List<WebElement> jobList) throws IOException, InterruptedException {
//
//        Pattern pattern = Pattern.compile("indeed.com");
//        String jobLink = _indeedBot.assembleJobLink(index, jobList);
//        Matcher matcher = pattern.matcher(_bot.getRequestURL(jobLink));

        boolean isEasyApply = jobList.get(index).findElements(By.className("iaLabel")).size() > 0;

        if (isEasyApply) {
            String jobLink = _indeedBot.getJobViewLink(index, jobList);
//            System.out.println(_bot.getRequestURL(jobLink));
            _indeedBot.clickOnApplyButton();
            _indeedBot.saveEZApplyJob(_bot.getRequestURL(jobLink), _appType);
        }
    }

    public void findAndSaveNotEasyApplyJob(int index, List<WebElement> jobList)
            throws IOException, InterruptedException {

        String jobLink = _indeedBot.assembleJobLink(index, jobList);
        boolean isEasyApply = _bot.getRequestURL(jobLink).contains("indeed.com");

        if (!isEasyApply) {
            _indeedBot.saveJob(_bot.getRequestURL(jobLink), _appType);
        }
    }

    public void findAllJobs(int index, List<WebElement> jobList) throws InterruptedException, IOException {

        String jobLink = _indeedBot.assembleJobLink(index, jobList);

        _indeedBot.saveJob(_bot.getRequestURL(jobLink), _appType);

    }
}
