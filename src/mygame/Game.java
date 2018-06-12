/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.util.List;
import java.util.Random;

//To use the example assets in a new jMonkeyPlatform project, right-click your project, select "Properties", go to "Libraries", press "Add Library" and add the "jme3-test-data" library.
/**
 * test
 *
 *
 */
public class Game
        extends SimpleApplication
        implements ActionListener,
                  PhysicsCollisionListener {// responsavel por executar os eventos de colisao

    public static void main(String[] args) {
        Game app = new Game();
         AppSettings settings = new AppSettings(true);
        settings.setResolution(1500, 900);   
        app.showSettings = false;
        app.setSettings(settings);
        app.start();
        
    }
    int time=1000;
    int pontos=0;
    private BitmapText record;
    private BitmapText tempo;
    private BitmapText fim;
    int mat[][] = {  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

                     {1,3,0,0,0,1,0,4,2,0,2,4,1,0,2,0,0,0,1,0,0,1,2,0,4,0,1},

                     {1,0,0,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1},

                     {1,2,0,0,0,1,0,0,0,0,2,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1},

                     {1,0,0,0,0,1,0,4,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,0,0,0,1},
                     {1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,2,0,0,0,0,1},

                     {1,0,0,0,0,1,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

                     {1,0,0,0,2,1,0,4,0,2,0,0,0,2,0,0,0,0,0,0,0,4,0,0,0,0,1},
                     {1,0,4,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,2,0,0,1,0,0,1},

                     {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,4,0,1,0,0,1},

                     {1,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1},
                     {1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,2,0,0,0,1},

                     {1,0,4,0,0,1,0,0,0,4,0,0,1,0,0,0,0,0,0,1,1,1,1,0,0,0,1},

                     {1,0,0,0,2,1,0,0,0,2,2,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},

                     {1,2,0,0,0,1,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

                     {1,0,0,0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},

                     {1,0,0,4,0,1,4,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

                     {1,0,0,0,2,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

                      {1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1},

                     {1,0,0,0,0,1,2,0,4,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

                     {1,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,0,2,4,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,2,0,0,1},

                     {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,1},

                     {1,0,0,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},

                     {1,2,0,0,0,0,0,0,0,2,0,0,1,0,0,4,0,2,0,0,0,0,0,2,0,0,1},
                     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
};
    private Spatial duck;
    private Spatial monkey;
    private BulletAppState bulletAppState;// fisica
    private PlayerCameraNode player;
    private boolean up = false, down = false, left = false, right = false;
    private Material boxMatColosion;
    Quaternion quat  = new Quaternion();
    Vector3f   vetor = new Vector3f(0,1,0);
    float angle=0;
    Vector3f vetorPlayer;

    
    
    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); // constroi
        chao();
        Placar();
        createLigth();
        createLab();
        
       
    
        initKeys();

        bulletAppState.setDebugEnabled(false);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
       player.upDateKeys(tpf, up, down, left, right);
       
        
        angle += tpf;
        quat.fromAngleAxis(+angle, vetor);
        duck.setLocalRotation(quat);
        if(time!=0){
            time-= 1;
            record.setText("Tempo = " + String.valueOf(time));
        }
        if(time==0){
            fim.setText("FIM DE JOGO !!!\nPontuacao: "+pontos);
            fim.setSize(50);
            guiNode.attachChild(fim);
        }
        
        

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void createPlayer(Vector3f vetorPlayer) {

        player = new PlayerCameraNode("player", assetManager, bulletAppState, cam, vetorPlayer);
        rootNode.attachChild(player);
        flyCam.setEnabled(false);

    }

    @Override
    public void onAction(String binding, boolean value, float tpf) {
        switch (binding) {
            case "CharLeft":
                if (value) {
                    left = true;
                } else {
                    left = false;
                }
                break;
            case "CharRight":
                if (value) {
                    right = true;
                } else {
                    right = false;
                }
                break;
        }
        switch (binding) {
            case "CharForward":
                if (value) {
                    up = true;
                } else {
                    up = false;
                }
                break;
            case "CharBackward":
                if (value) {
                    down = true;
                } else {
                    down = false;
                }
                break;
        }




    }

    private void createLigth() {

        DirectionalLight l1 = new DirectionalLight();
        l1.setDirection(new Vector3f(1, -0.7f, 0));
        rootNode.addLight(l1);

        DirectionalLight l2 = new DirectionalLight();
        l2.setDirection(new Vector3f(-1, 0, 0));
        rootNode.addLight(l2);

        DirectionalLight l3 = new DirectionalLight();
        l3.setDirection(new Vector3f(0, 0, -1.0f));
        rootNode.addLight(l3);

        DirectionalLight l4 = new DirectionalLight();
        l4.setDirection(new Vector3f(0, 0, 1.0f));
        rootNode.addLight(l4);


        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);


    }
    
     protected void Placar() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        record = new BitmapText(guiFont, false);
        record.setSize(guiFont.getCharSet().getRenderedSize());        
        record.setColor(ColorRGBA.Black);
        record.setSize(50);
        record.setLocalTranslation(0, settings.getHeight() - 200, 0);
        guiNode.attachChild(record);

        fim = new BitmapText(guiFont, false);
        fim.setSize(guiFont.getCharSet().getRenderedSize());

        fim.setLocalTranslation((settings.getWidth() / 2) - (guiFont.getCharSet().getRenderedSize() * (fim.getText().length() / 2 + 13)),
                settings.getHeight() + fim.getLineHeight() / 2 - 300, 0);
    }
    
    public void createLab(){
        for(int l=0;l<mat.length;l++){
            for(int c=0;c<mat.length;c++){
                
                int b=mat[l][c];
                if(b==1){
                  createCubo(l*2,1,c*2);
                }
                else if(b==2){
                    createDuck(l*2,1,c*2);
                }
                else if(b==3){
                    vetorPlayer = new Vector3f(l*2,2,c*2);
                    createPlayer(vetorPlayer);
                    
                    
                }
                else if(b==4){
                createMonkey(l*2,1,c*2);
                }
            }
        
        }
    }
    
    public void createDuck(float x, float y, float z){
       duck = assetManager.loadModel("Models/Duck.gltf");
       duck.setName("Duck");
        duck.scale(0.9f,0.9f,0.9f);
      //  duck.rotate(0.0f,-3.0f, 0.0f);
        duck.setLocalTranslation(x, y, z);
               
        rootNode.attachChild(duck);
        
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(1);// tudo q tem fisica tem q ter corpo rigido/ massa 1, cidade tem massa 0
        duck.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);
    }
    
    public void createMonkey(float x, float y, float z){
       monkey = assetManager.loadModel("Models/MonkeyHead.mesh.xml");
       monkey.setName("Monkey");
       monkey.scale(0.6f,0.6f,0.6f);
      //  duck.rotate(0.0f,-3.0f, 0.0f);
        monkey.setLocalTranslation(x, y, z);
               
        rootNode.attachChild(monkey);
        
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(1);// tudo q tem fisica tem q ter corpo rigido/ massa 1, cidade tem massa 0
        monkey.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);
    }

    private void createCubo(float x, float y, float z) {
        /* A colored lit cube. Needs light source! */
        Box boxMesh = new Box(1f, 5f, 1f);
        Geometry boxGeo = new Geometry("Box", boxMesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap",assetManager.loadTexture("Textures/predio.jpg"));
        boxGeo.setMaterial(mat);
        boxGeo.setLocalTranslation(x,y,z);
        rootNode.attachChild(boxGeo);
        


        RigidBodyControl boxPhysicsNode = new RigidBodyControl(50000);// tudo q tem fisica tem q ter corpo rigido/ massa 1, cidade tem massa 0
        boxGeo.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);// adiciono esse corpo risido no phisic space

    }


    private void chao(){
        Box boxMesh = new Box(100f, 1f, 100f);
            Geometry boxGeo = new Geometry("Box", boxMesh);
            Material boxMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            boxMat.setBoolean("UseMaterialColors", true);
            boxMat.setColor("Ambient", ColorRGBA.Gray);
            boxMat.setColor("Diffuse", ColorRGBA.Gray);
            boxGeo.setMaterial(boxMat);
            rootNode.attachChild(boxGeo);
            
        RigidBodyControl boxPhysicsNode = new RigidBodyControl(0);// tudo q tem fisica tem q ter corpo rigido/ massa 1, cidade tem massa 0
        boxGeo.addControl(boxPhysicsNode);
        bulletAppState.getPhysicsSpace().add(boxPhysicsNode);
        
        
        
    }
    
    
    
    private void initKeys() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");

    }
/* A colored lit cube. Needs light source! */ 
    
    
    @Override
    public void collision(PhysicsCollisionEvent event) {
    
        if(time!=0){
        if(event.getNodeA().getName().equals("player") || 
           event.getNodeB().getName().equals("player")){
        
            if(event.getNodeA().getName().equals("Duck")){
                Spatial s = event.getNodeA();
                rootNode.detachChild(s);
                pontos+=2;

               }
            else if(event.getNodeB().getName().equals("Duck")){
                
                   Spatial s = event.getNodeB();
                   rootNode.detachChild(s);
                   pontos+=2;
          
        }
            else if(event.getNodeA().getName().equals("Monkey")){
                Spatial s = event.getNodeA();
                rootNode.detachChild(s);
                pontos-=1;

               }
            else {
                if(event.getNodeB().getName().equals("Monkey")){
                   Spatial s = event.getNodeB();
                   rootNode.detachChild(s);
                   pontos-=1;
          
            }
            
        }
        
    }
        }
}

}








