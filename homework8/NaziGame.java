package homework8;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Phaser;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class NaziGame {
    private static final Phaser PHASER = new Phaser(1);//Сразу регистрируем главный поток
    //Фазы 0 и 6 - это продолжительность игры

    public static void main(String[] args) {
        ArrayList<Thread> pass = new ArrayList<>();
        ArrayList<Passenger> passengers = new ArrayList<>();

        Passenger passenger1 = new Passenger(1, 3, "A");
        Passenger passenger2 = new Passenger(1, 5, "B");
        Passenger passenger3 = new Passenger(1, 4, "C");
        Passenger passenger4 = new Passenger(1, 6, "D");
        Passenger passenger5 = new Passenger(1, 2, "E");
        Thread pass1 = new Thread(passenger1);
        Thread pass2 = new Thread(passenger2);
        Thread pass3 = new Thread(passenger3);
        Thread pass4 = new Thread(passenger4);
        Thread pass5 = new Thread(passenger5);
        passengers.add(passenger1);
        passengers.add(passenger2);
        passengers.add(passenger3);
        passengers.add(passenger4);
        passengers.add(passenger5);
        
        pass.add(pass1);
        pass.add(pass2);
        pass.add(pass3);
        pass.add(pass4);
        pass.add(pass5);
        for (Thread p : pass) {
            PHASER.register();//Регистрируем поток, который будет участвовать в фазах
            p.start();        // и запускаем
        } 

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Start playing.");
				
                    PHASER.arrive();//В фазе 0 всего 1 участник - автобус
                    break;
                case 6:
                    System.out.println("Finish playing.");
                    PHASER.arriveAndDeregister();//Снимаем главный поток, ломаем барьер
                    break;
                default:
                	Sound.playSound("C:\\Users\\Konstantin\\Desktop\\Rammstein.wav").join();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//                    if(currentBusStop>0) {
//                    System.out.println("Stop № " + (currentBusStop));
//                    }
				PHASER.arriveAndAwaitAdvance();//Сообщаем о своей готовности
				System.out.println("Stop!!!");
				

                    
                    try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                    for (Thread p : pass) {
                    	
                    	if(p.isAlive()) {
                    		
                    	} else {
                    		System.out.println("continue dancing");
                    		if(pass.size()!=1) {
                    		pass.remove(p);
                    		break;
                    		} else {
                    			System.out.println("Winner");
                    		}
                    	}
                    	
//                    	if(Thread.activeCount()==2) {
//                    		System.out.println("last");
//                    	}
     
                    }
            }
        }
        System.out.println(Thread.activeCount());
    }
    
    public static class Passenger implements Runnable{
        private int departure;
        private int destination;
        private String name;

        

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Passenger(int departure, int destination, String name) {
            this.departure = departure;
            this.destination = destination;
            this.name = name;

        }

        @Override
        public void run() {
        	
        	System.out.println(name+" is take part in the game");

                while (PHASER.getPhase() < destination)
                	
                    PHASER.arriveAndAwaitAdvance();     //заявляем в каждой фазе о готовности и ждем

                System.out.println(name + " finished playing.");
                
                PHASER.arriveAndDeregister();   //Отменяем регистрацию на нужной фазе
        }

        @Override
        public String toString() {
            return "Nazi{" + departure + " -> " + destination + '}';
        }
    }
    
    
    static class Sound implements AutoCloseable {
		private boolean released = false;
		private AudioInputStream stream = null;
		private Clip clip = null;
		private FloatControl volumeControl = null;
		private boolean playing = false;
		
		public Sound(File f) {
			try {
				stream = AudioSystem.getAudioInputStream(f);
				clip = AudioSystem.getClip();
				clip.open(stream);
				clip.addLineListener((LineListener) new Listener());
				volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				released = true;
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
				exc.printStackTrace();
				released = false;
				
				close();
			}
		}

		// true если звук успешно загружен, false если произошла ошибка
		public boolean isReleased() {
			return released;
		}
		
		// проигрывается ли звук в данный момент
		public boolean isPlaying() {
			return playing;
		}

		public void play(boolean breakOld) {
			if (released) {
				if (breakOld) {
					clip.stop();
					clip.setFramePosition(0);
					clip.start();
					playing = true;
				} else if (!isPlaying()) {
					clip.setFramePosition(0);
					clip.start();
					playing = true;
				}
			}
		}
		
		// То же самое, что и play(true)
		public void play() {
			play(true);
		}
		
		// Останавливает воспроизведение
		public void stop() {
			if (playing) {
				clip.stop();
			}
		}
		
		public void close() {
			if (clip != null)
				clip.close();
			
			if (stream != null)
				try {
					stream.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
		}

		// Установка громкости
		/*
		  x долже быть в пределах от 0 до 1 (от самого тихого к самому громкому)
		*/
		public void setVolume(float x) {
			if (x<0) x = 0;
			if (x>1) x = 1;
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			volumeControl.setValue((max-min)*x+min);
		}
		
		// Возвращает текущую громкость (число от 0 до 1)
		public float getVolume() {
			float v = volumeControl.getValue();
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			return (v-min)/(max-min);
		}

		// Дожидается окончания проигрывания звука
		public void join() {
			if (!released) return;
			synchronized(clip) {
				try {
					while (playing)
						clip.wait();
				} catch (InterruptedException exc) {}
			}
		}
		
		// Статический метод, для удобства
		public static Sound playSound(String path) {
			File f = new File(path);
			Sound snd = new Sound(f);
			snd.play();
			return snd;
		}

		private class Listener implements LineListener {
			public void update(LineEvent ev) {
				if (ev.getType() == LineEvent.Type.STOP) {
					playing = false;
					synchronized(clip) {
						clip.notify();
					}
				}
			}

		}
	}
    
}
