a320
====

Framework for loading JSON objects asynchronously and converting them into POJOs

Usage:
------

User has to perform following steps to use this framework within there project:

1)	Extend POJO with a framework class "RemoteObject"

2)	Apply @Expose annotations to each property that needs to be serialize/deserialize

3)	To get a remote object asynchronously, create a class that will extend the "RemoteObjectCollection<T>" of framework.
	"T" will be a POJO that extends RemoteObject type.

	3.1)	Override the "doInBackground(..)"
	
	3.2)	Call a "getRemoteObject" inside it.
	
	3.3)	Pass the "url" and the "return type of the data" that is in json to the "getRemoteObject".
	
4) Implement you Activity with "RemoteObjectDelegate" interface of this framework.

	4.1)	Override "update(..)" method.
	
	4.2)	User will get the asynchronous results in update(..) method. Match the type of result and update the UI.  