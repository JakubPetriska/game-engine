# Change Log

## Version 0.4.1
* Showcase game added to the engine.
* Engine renamed.
* Project migrated to GitHub with all the documentation.

## Version 0.4.0
* Updated gradle and Android tools versions.
* Colors of objects added

## Version 0.3.1
* Bug fixes

## Version 0.3.0
* Minimal SDK version raised to Android 2.3.
* Bug in collider unregistering and onCollisionEnd calling fixed.
* Bug in game object removing fixed.
* Collider group is now a string name of the group so there can be any number of collider groups.
* TouchInput fixed.
* Documentation edits.
* InputMessenger renamed to ExternalMessenger.
* Better explanation of problem when engine throws an exception.

## Version 0.2.1
* Static and dynamic collider groups added.
* Support extended to Android 2.2.
* Missing Messenger documentation added.

## Version 0.2.0
* Rotation and scale added.
* Support of custom models in obj format.
* Collisions added. Currently supports only box collider.
* GameObject now has tag. This allows recognizing the object for example during collision.
* Camera component added. Has near and far parameters that can be set through code or xml.
* Mesh component renamed to Model.
* Transform element and it's parts are no longer mandatory in scene definition xml file.
* Coordinate system changed to left handed with z pointing forward, y up and x to the right.
* LWJGL util library removed. Engine uses it's own vector and matrix class.
* Param removed from scene definition xm files. Params are listed directly in component tag.
* Allowed script parameter types are now boolean, int, long, float, double or their respective wrapper classes.
* API for retrieval of device's display parameters added.
* Primitive cube size changed to 1.

## Version 0.1.0
* Supports basic features.
* Objects can be only moved not rotated or scaled.
* Camera and lighting is immovable.