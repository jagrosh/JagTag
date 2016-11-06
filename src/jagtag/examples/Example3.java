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
 * This example shows off a few of the features of the JagTag parsing system.
 * For starters, it shows that a single method can have both simple and
 * complex parts. In the example, the method "repeat" either repeats the input
 * text the provided number of times, or simply repeats the last output of a
 * complex repeat evaluation.
 * 
 * Additionally, this demonstrates the importance of clearing the cache if
 * you don't want the parsing environment to persist.
 * 
 * @author John Grosh (jagrosh)
 */
public class Example3 {
    
    public static void main(String[] args) {
        Parser parser = new ParserBuilder()
                .addMethod(new Method("repeat",
                        (e) -> e.getOrDefault("last",""), 
                        (e,i) -> {
                            String output = i[0];
                            try{
                                for(int k=1; k<Integer.parseInt(i[1]); k++)
                                    output+=i[0];
                            } catch(NumberFormatException ex) {}
                            e.put("last", output);
                            return output;
                        }, "|times:"))
                .build();
        System.out.println(parser.parse("{repeat:hello |times:4}"));
        System.out.println(parser.parse("Repetition!: {repeat}"));
        System.out.println(parser.clear().parse("Repetition?: {repeat}"));
    }
}
