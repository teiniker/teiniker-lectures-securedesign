package org.se.lab;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


public class TemplateTest
{
	@Before
	public void setup()
	{

	}

	@Test
	public void testTemplate()
	{
		String templateName = "hello.template";

		TemplateEngine templateEngine = new TemplateEngine();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode("TEXT");
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable("name", "World");
		StringWriter stringWriter = new StringWriter();
		templateEngine.process(templateName, context, stringWriter);
		String html = stringWriter.toString();

		System.out.println(html);
	}
}
