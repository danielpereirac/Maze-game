/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

/**
 *
 * @author Abutua
 *
 *
 * Ver:
 * https://wiki.jmonkeyengine.org/doku.php/jme3:advanced:making_the_camera_follow_a_character
 */
public class PlayerCameraNode extends Node {

    private final BetterCharacterControl physicsCharacter;
  //  private final AnimControl animationControl;
    //private final AnimChannel animationChannel;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 0);
    private float airTime;
    
    public PlayerCameraNode(String name, AssetManager assetManager, BulletAppState bulletAppState, Camera cam, Vector3f posicao) {
        super(name);

        Node carro = (Node) assetManager.loadModel("Models/Ferrari/Car.mesh.xml");
        carro.setLocalTranslation(0, 0f, 0);
        carro.rotate(0f,3.25f,0);
       
        
        super.rotate(0.0f,1.5f, 0.0f);
        super.scale(0.5f);
        super.setLocalTranslation(posicao);
        super.attachChild(carro);

        physicsCharacter = new BetterCharacterControl(1, 2.5f, 16f);

        super.addControl(physicsCharacter);

        bulletAppState.getPhysicsSpace().add(physicsCharacter);

       // animationControl = oto.getControl(AnimControl.class);
     //   animationChannel = animationControl.createChannel();

        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 5, -10));
        camNode.lookAt(carro.getLocalTranslation(), Vector3f.UNIT_Y);

        super.attachChild(camNode);

    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }

    public Vector3f getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(Vector3f viewDirection) {
        this.viewDirection = viewDirection;
    }

//    void upDateAnimationPlayer() {
//
//        if (walkDirection.length() == 0) {
//            if (!"stand".equals(animationChannel.getAnimationName())) {
//                animationChannel.setAnim("stand", 1f);
//            }
//        } else {
//            if (airTime > .3f) {
//                if (!"stand".equals(animationChannel.getAnimationName())) {
//                    animationChannel.setAnim("stand");
//                }
//            } else if (!"Walk".equals(animationChannel.getAnimationName())) {
//                animationChannel.setAnim("Walk", 0.7f);
//            }
//        }
//
//    

    void upDateKeys(float tpf, boolean up, boolean down, boolean left, boolean right) {
        Vector3f camDir = getWorldRotation().mult(Vector3f.UNIT_Z);

        viewDirection.set(camDir);
        walkDirection.set(0, 0, 0);

        walkDirection.addLocal(camDir.mult(7));

        if (up) {
            walkDirection.addLocal(camDir.mult(15));
        } else if (down) {
            walkDirection.addLocal(camDir.mult(3).negate());
        }

        if (left) {
            Quaternion rotateL = new Quaternion().fromAngleAxis(FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateL.multLocal(viewDirection);
        } else if (right) {
            Quaternion rotateR = new Quaternion().fromAngleAxis(-FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateR.multLocal(viewDirection);
        }

        physicsCharacter.setWalkDirection(walkDirection);
        physicsCharacter.setViewDirection(viewDirection);

    //    upDateAnimationPlayer();

    }

  

}