package org.khanacademy.exercises;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTests {

	WebDriver driver;
	String baseUrl;
	WebElement answerbox;
	WebElement checkAnswerButton;
	WebElement nextQuestionButton;
	
	@Before
	public void setUp() throws Exception {
		this.driver = new FirefoxDriver();
//		this.baseUrl = "http://www.khanacademy.org";
		this.baseUrl = "http://sandcastle.khanacademy.org/media/castles/origin:master/exercises";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAbsoluteValue() {
		this.driver.get(this.baseUrl + "/absolute_value.html?seed=1");
		this.verifyBasics("Absolute value");
		this.verifyWrongAnswer("8");
		this.verifyCorrectAnswer("8.5");
	}

	@Test
	public void testParallelLines1() {
		this.driver.get(this.baseUrl + "/parallel_lines_1.html?seed=1");
		this.verifyBasics("Parallel lines 1");
		WebElement graphieDiv = this.driver.findElement(By.id("parallel-lines"));
		// Verify that the first child of the graphie div is an svg node.
		Assert.assertEquals("svg", graphieDiv.findElement(By.xpath("*[1]")).getTagName());
		this.verifyWrongAnswer("130");
		this.verifyCorrectAnswer("131");
	}
	
	void verifyCorrectAnswer(String answer) {
		this.submitAnswer(answer);
		Assert.assertFalse(this.checkAnswerButton.isDisplayed());
		Assert.assertTrue(this.nextQuestionButton.isDisplayed());
	}
	
	void verifyWrongAnswer(String answer) {
		this.submitAnswer(answer);
		Assert.assertTrue(this.checkAnswerButton.isDisplayed());
		Assert.assertFalse(this.nextQuestionButton.isDisplayed());
		Assert.assertEquals("Try Again", this.checkAnswerButton.getAttribute("value"));
	}

	void submitAnswer(String answer) {
		this.answerbox.sendKeys(answer);
		Assert.assertTrue(this.checkAnswerButton.isEnabled());
		this.checkAnswerButton.click();
	}

	void verifyBasics(String title) {
		// verify title
		WebElement titleElement = this.driver.findElement(By.className("practice-exercise-topic-context"));
		Assert.assertEquals("Practicing", titleElement.getText());
		Assert.assertEquals("Practicing " + title, titleElement.findElement(By.xpath("..")).getText().trim());
		
		// verify the answer box is there
		WebElement answerform = this.driver.findElement(By.id("answerform"));
		Assert.assertNotNull(answerform);
		Assert.assertEquals("form", answerform.getTagName());
		WebElement solutionarea = answerform.findElement(By.id("solutionarea"));
		Assert.assertNotNull(solutionarea);
		this.answerbox = solutionarea.findElement(By.tagName("input"));
		Assert.assertNotNull(this.answerbox);
		this.checkAnswerButton = answerform.findElement(By.id("check-answer-button"));
		Assert.assertNotNull(this.checkAnswerButton);
		Assert.assertTrue(this.checkAnswerButton.isDisplayed());
		Assert.assertFalse(this.checkAnswerButton.isEnabled());
		this.nextQuestionButton = answerform.findElement(By.id("next-question-button"));
		Assert.assertNotNull(this.nextQuestionButton);
		Assert.assertFalse(this.nextQuestionButton.isDisplayed());
		
		// verify question is there
		WebElement problemAndAnswer = this.driver.findElement(By.id("problem-and-answer"));
		Assert.assertNotNull(problemAndAnswer);
		WebElement problemarea = problemAndAnswer.findElement(By.id("problemarea"));
		Assert.assertNotNull(problemarea);
		WebElement workarea = problemAndAnswer.findElement(By.id("workarea"));
		Assert.assertNotNull(workarea);
		WebElement question = problemAndAnswer.findElement(By.className("question"));
		Assert.assertNotNull(question);
	}

	@After
	public void tearDown() throws Exception {
		this.driver.quit();
	}
	
	void pause(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
