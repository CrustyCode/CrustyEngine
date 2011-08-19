package org.crusty.engine.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import org.crusty.engine.sprite.Sprite;
import org.crusty.math.Vec2;
import org.crusty.math.Vec2int;

public class ParticleManager {

	private boolean particleDebug = false;
	
	ArrayList<Particle> particles = new ArrayList<Particle>();
	Random r = new Random();
	
//	if (type.equals("rocketFire")) {
//		vel = new Vec2(Math.sin(r.nextInt(360 * 2))*0.02f, (Math.sin(r.nextInt(360 * 2)) + 1)*0.02f);
//		acc = new Vec2(0, 0);
//	} else if (type.equals("rocketSmoke")) {
//		vel = new Vec2(Math.sin(r.nextInt(360 * 2))*0.05f, (Math.sin(r.nextInt(360 * 2)) + 1)*0.01f);
//		acc = new Vec2(0, 0);
//		alpha = 0.5f;
//	} else if (type.equals("jettisonSparks")) {
//		vel = new Vec2((1 - r.nextDouble()*2)*0.05, (1 - r.nextDouble()*2)*0.05);
//		acc = new Vec2(0, 0);
//		alpha = 1f;
//	} else if (type.equals("capsuleJet")) {
//		vel = new Vec2(-dir.x, -dir.y);
//		acc = new Vec2(0, 0);
//		alpha = 0.2f;
//	} else if (type.equals("crash")) {
//		vel = new Vec2((1 - r.nextDouble()*2)*0.05, (1 - r.nextDouble()*2)*0.05);
//		acc = new Vec2(0, 0);
//		alpha = 0.5f;
//	} else if (type.equals("wormhole")) {
//		vel = new Vec2((1 - r.nextDouble()*2)*0.05, (1 - r.nextDouble()*2)*0.05);
//		acc = new Vec2(0, 0);
//		alpha = 0.5f;
//	}
	
	public ParticleManager() {
		
	}
	
	public void logic(double dt) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).logic(dt);
			if (particles.get(i).alpha == 0) {
				particles.remove(particles.get(i));
			}
		}
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(g);
		}
		if (particleDebug) {
			g.setColor(Color.BLACK);
			g.fillRect(5, 400, 100, 20);
			g.setColor(Color.WHITE);
			g.drawRect(5, 400, 100, 20);
			g.drawString("Particles: " + particles.size(), 10, 415);
		}
	}
	
	public void createBurst(int num, Sprite sprite, Vec2 pos) {
		for (int i = 0; i < num; i++) {
			Vec2 vel = new Vec2(r.nextDouble() - 0.5, r.nextDouble() - 0.5);
			vel = vel.normalise();
			Vec2 acc = new Vec2(-vel.x * 0.3, -vel.y * 0.3);
			vel.x *= 0.02;
			vel.y *= 0.02;
			Particle p = new Particle(sprite, pos, vel, acc, new Vec2int(sprite.getWidth()/2, sprite.getHeight()/2), 
					1.5f, (r.nextDouble() - 0.5)/200);
			particles.add(p);
//			System.out.println("Pos: " + pos.toString() + " vel: " + vel.toString());
		}
	}

	public void createBloodBurst(int num, Sprite sprite, Vec2 pos) {
		for (int i = 0; i < num; i++) {
			Vec2 vel = new Vec2(r.nextDouble() - 0.5, r.nextDouble() - 1);
			vel = vel.normalise();
			Vec2 acc = new Vec2(0, 5);
			vel.x *= 0.2 + r.nextDouble()/10;
			vel.y *= 0.2 + r.nextDouble()/10;
			Particle p = new Particle(sprite, pos, vel, acc, new Vec2int(sprite.getWidth()/2, sprite.getHeight()/2), 
					1.5f, (r.nextDouble() - 0.5)/100);
			particles.add(p);
//			System.out.println("Pos: " + pos.toString() + " vel: " + vel.toString());
		}
	}
	
//	public void addParticles(int num, Sprite sprite, Vec2 pos, String type) {
//		addParticle(num, sprite, pos, type, null);
//	}
//	
//	public void addParticles(int num, Sprite sprite, Vec2 pos, String type, Vec2 dir) {
//		for (int i = 0; i < num; i++) {
//			Particle p = new Particle(sprite, pos, type, dir);
//			particles.add(p);
//			System.out.println(".> ");
//		}
//	}
	
}
