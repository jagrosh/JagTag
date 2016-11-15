/*
 * Copyright 2016 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jagrosh.jagtag.examples;

import me.jagrosh.jagtag.Method;
import me.jagrosh.jagtag.Parser;
import me.jagrosh.jagtag.ParserBuilder;

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
                        (env) -> env.getOrDefault("last",""), 
                        (env,in) -> {
                            String output = in[0];
                            try{
                                for(int k=1; k<Integer.parseInt(in[1]); k++)
                                    output+=in[0];
                            } catch(NumberFormatException ex) {}
                            env.put("last", output);
                            return output;
                        }, "|times:"))
                .build();
        System.out.println(parser.parse("{repeat:hello |times:4}"));
        System.out.println(parser.parse("Repetition!: {repeat}"));
        System.out.println(parser.clear().parse("Repetition?: {repeat}"));
    }
}
