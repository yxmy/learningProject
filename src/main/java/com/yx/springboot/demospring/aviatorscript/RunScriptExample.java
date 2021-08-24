package com.yx.springboot.demospring.aviatorscript;

import com.googlecode.aviator.AviatorEvaluator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yuanxin
 * @date 2021/7/16
 */
public class RunScriptExample {

    public static void main(String[] args) throws Exception {
//        Expression expression = AviatorEvaluator.getInstance().compileScript("scriptfolder/closure_oop.av");
//        expression.execute();


//        String expression = "name != nil ? 'Hello, #{name}' : 'who are u'";
//        final Expression compile = AviatorEvaluator.compile(expression);
//        final String execute = (String) compile.execute();
//        System.out.println(execute);
//        final String execute1 = (String) compile.execute(compile.newEnv("name", "yuanxin"));
//        System.out.println(execute1);


//        TestAviator foo = new TestAviator(10, 19.2f, new Date());
//        Map<String, Object> params = new HashMap<>();
//        params.put("foo", foo);
//        System.out.println(AviatorEvaluator.execute("'foo.i is #{foo.i}'", params));
//        System.out.println(AviatorEvaluator.execute("'foo.f is #{foo.f}'", params));
//        System.out.println(AviatorEvaluator.execute("'foo.year is #{foo.date.year}'", params));


        AviatorEvaluator.defineFunction("add", "lambda (x,y) -> println(x + y) end");
        AviatorEvaluator.execute("add(1,2)"); // 结果为 3
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class TestAviator {
        int i;
        float f;
        Date date;
    }
}
