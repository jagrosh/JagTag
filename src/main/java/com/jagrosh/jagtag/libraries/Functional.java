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
package com.jagrosh.jagtag.libraries;

import com.jagrosh.jagtag.Method;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

/**
 * Functional Library
 * Methods for conditionals and mathematics
 * 
 * @author John Grosh (jagrosh)
 */
public class Functional {
    
    public static Collection<Method> getMethods() {
        return Arrays.asList(
            // equivalent to a comment; gets removed at runtime
            new Method("note", (env,in) -> ""),
                
            // chooses randomly between options
            new Method("choose", (env,in) -> {return in.length==0 ? "" : in[(int)(Math.random()*in.length)];}, true),
            
            // picks a random number in the provided range
            new Method("range", (env,in) -> {
                try{
                    int first = (int)(Double.parseDouble(in[0].trim()));
                    int second = (int)(Double.parseDouble(in[1].trim()));
                    if(second<first)
                    {
                        int tmp = second;
                        second = first;
                        first = tmp;
                    }
                    return Integer.toString(first+(int)(Math.random()*(second-first)));
                } catch(NumberFormatException ex) {
                    return in[0]+"|"+in[1];
                }
            }, "|"),
            
            // performs a conditional
            new Method("if", (env,in) -> {
                if(in[0].equalsIgnoreCase("true"))
                    return in[1];
                if(in[0].equalsIgnoreCase("false"))
                    return in[2];
                if(evaluateStatement(in[0]))
                    return in[1];
                return in[2];
            }, "|then:", "|else:"),
            
            // performs basic mathematical function
            new Method("math", (env,in) -> {
                return evaluateMath(in[0]);
            }),
                
            // returns the absolute value of the provided number
            new Method("abs", (env,in) -> {
                try {
                    return Integer.toString(Math.abs(Integer.parseInt(in[0])));
                } catch(NumberFormatException e){}
                try {
                    return Double.toString(Math.abs(Double.parseDouble(in[0])));
                } catch(NumberFormatException e){}
                return in[0];
            }),
            
            // rounds a number down
            new Method("floor", (env,in) -> {
                try {
                    return Integer.toString((int)Math.floor(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // rounds a number up
            new Method("ceil", (env,in) -> {
                try {
                    return Integer.toString((int)Math.ceil(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // rounds a number up if the decimal part is .5 or greater, down otherwise
            new Method("round", (env,in) -> {
                try {
                    return Integer.toString((int)Math.round(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // takes the sine of radians
            new Method("sin", (env,in) -> {
                try {
                    return Double.toString(Math.sin(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // takes the cosine of radians
            new Method("cos", (env,in) -> {
                try {
                    return Double.toString(Math.cos(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // takes the tangent of radians
            new Method("tan", (env,in) -> {
                try {
                    return Double.toString(Math.tan(Double.parseDouble(in[0])));
                } catch(NumberFormatException e) {
                    return in[0];
                }
            }),
            
            // converts a number to a different base
            new Method("base", (env, in) -> {
                try {
                    return new BigInteger(in[0], Integer.parseInt(in[1])).toString(Integer.parseInt(in[2]));
                } catch(Exception e) {
                    return in[0];
                }
            },true)
        );
    }
    
    private static String evaluateMath(String statement)
    {
        int index = statement.lastIndexOf("|+|");
        if(index==-1)
            index = statement.lastIndexOf("|-|");
        if(index==-1)
            index = statement.lastIndexOf("|*|");
        if(index==-1)
            index = statement.lastIndexOf("|%|");
        if(index==-1)
            index = statement.lastIndexOf("|/|");
        if(index==-1)
            index = statement.lastIndexOf("|^|");
        if(index==-1)
            return statement;
        String first = evaluateMath(statement.substring(0,index)).trim();
        String second = evaluateMath(statement.substring(index+3)).trim();
        Double val1;
        Double val2;
        try{
            val1 = Double.parseDouble(first);
            val2 = Double.parseDouble(second);
            switch (statement.substring(index, index+3)){
                case "|+|":
                    return ""+(val1+val2);
                case "|-|":
                    return ""+(val1-val2);
                case "|*|":
                    return ""+(val1*val2);
                case "|%|":
                    return ""+(val1%val2);
                case "|/|":
                    return ""+(val1/val2);
                case "|^|":
                    return ""+(Math.pow(val1, val2));
            }
        }catch(NumberFormatException e){}
        switch (statement.substring(index, index+3)){
                case "|+|":
                    return first+second;
                case "|-|":
                    int loc = first.indexOf(second);
                    if(loc!=-1)
                        return first.substring(0,loc)+(loc+second.length()<first.length()?first.substring(loc+second.length()):"");
                    return first+"-"+second;
                case "|*|":
                    return first+"*"+second;
                case "|%|":
                    return first+"%"+second;
                case "|/|":
                    return first+"/"+second;
                case "|^|":
                    return first+"^"+second;
            }
        return statement;
    }
    
    private static boolean evaluateStatement(String statement)
    {
        int index = statement.indexOf("|=|");
        if(index==-1)
            index = statement.indexOf("|<|");
        if(index==-1)
            index = statement.indexOf("|>|");
        if(index==-1)
            index = statement.indexOf("|~|");
        if(index==-1)
            index = statement.indexOf("|?|");
        if(index==-1)
            return false;
        String s1 = statement.substring(0, index).trim();
        String s2 = statement.substring(index+3).trim();
        
        try{
            double i1 = Double.parseDouble(s1);
            double i2 = Double.parseDouble(s2);
            switch(statement.substring(index, index+3))
            {
            case "|=|":
                return (i1==i2);
            case "|~|":
                return (((int)(i1*100))==((int)(i2*100)));
            case "|>|":
                return (i1>i2);
            case "|<|":
                return (i1<i2);
            }
        }catch(NumberFormatException e){}
        
        switch(statement.substring(index, index+3))
        {
            case "|=|":
                return (s1.equals(s2));
            case "|~|":
                return (s1.equalsIgnoreCase(s2));
            case "|>|":
                return (s1.compareTo(s2)>0);
            case "|<|":
                return (s1.compareTo(s2)<0);
            case "|?|":
                try{return s1.matches(s2);}catch(Exception e){return false;}
        }
        return false;
    }
}
