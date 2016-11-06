/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jagtag.examples;

import jagtag.Method;
import jagtag.Parser;
import jagtag.ParserBuilder;

/**
 *
 * This example shows some simple method creation without using any of the
 * included libraries. Specifically, this example creates a random decimal
 * number between 0 and 100 using two methods, randint and randdecimal.
 * 
 * @author John Grosh (jagrosh)
 */
public class Example2 {
    
    public static void main(String[] args) {
        Parser parser = new ParserBuilder()
                .addMethod(new Method("randint", (e) -> Integer.toString((int)(Math.random()*100))))
                .addMethod(new Method("randdecimal", (e) -> Double.toString(Math.random()).substring(1)))
                .build();
        System.out.println(parser.parse("{randint}{randdecimal}"));
    }
}
