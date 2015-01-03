package com.jeb.engine.config.model.initial_scene_state;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ISScene {
	
	@ElementList
	public List<ISGameObject> gameObjects;
}
