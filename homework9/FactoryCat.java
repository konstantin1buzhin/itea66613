package homework9;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class FactoryCat {
		
		   public Animal getAnimal(String catType){
		      if(catType == null){
		         return null;
		      }		
		      if(catType.equalsIgnoreCase("CAT")){
		         return new Cat();
		         
		      } else if(catType.equalsIgnoreCase("FATCAT")){
		         return new FatCat();
		         
		      } else if(catType.equalsIgnoreCase("HOMELESSCAT")){
		         return new HomelessCat();
		      }
		      
		      return null;
		   }
		   
	public void search(Animal[] classes, ArrayList<Animal>farsh) {
		   for(Animal a:classes) {
		    	 Class clazz = a.getClass();

		         Method[] methods = clazz.getMethods();
		    		for(Method field:methods) {
		    			if(field.isAnnotationPresent(Paw.class)) {
		    				if(field.getAnnotation(Paw.class).countRaws()!=2) {
		    					farsh.add(a);
		    				}
		    			}
		    		}
		    		Field[] fields = clazz.getDeclaredFields();
		    		for(Field field:fields) {
		    			if(field.isAnnotationPresent(LuckyCat.class)) {
		        			if(!field.getAnnotation(LuckyCat.class).luckyAnimal()){
		        				if(!farsh.contains(a)) {
		        				farsh.add(a);
		        				}
		        			} else {
		        				farsh.remove(a);
		        			}
		        			}
		    		}
		    		for(Annotation ann : clazz.getAnnotations()) {
		    			Class<? extends Annotation> type = ann.annotationType();
		    			String blochoble = Blochoble.class.getSimpleName();
		    			if(type.getSimpleName().equals(blochoble)) {
		    			if(!farsh.contains(a)) {
		    				farsh.add(a);
		    			} else {
		    				farsh.remove(a);
		    			}
		    			} 
		    		}
		    		
		     }
	}
}
