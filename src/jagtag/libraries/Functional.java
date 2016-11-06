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
package jagtag.libraries;

import jagtag.Method;
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
            new Method("choose", (env,in) -> {return in[(int)(Math.random()*in.length)];}, new String[0]),
            
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
                if(evaluateStatement(in[0]))
                    return in[1];
                else return in[2];
            }, "|then:", "|else:"),
            
            // performs basic mathematical function
            new Method("math", (env,in) -> {
                return evaluateMath(in[0]);
            })
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
            return statement;
        String first = evaluateMath(statement.substring(0,index));
        String second = evaluateMath(statement.substring(index+3));
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
            }
        }catch(Exception e){}
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
        String s1 = statement.substring(0, index);
        String s2 = statement.substring(index+3);
        
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
