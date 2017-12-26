package com.sqtoon.socket.common.util;

import java.io.Serializable;

public class CinLinkedNode<K> implements Serializable
{

	private static final long serialVersionUID = 8268135584345517752L;
	CinLinkedNode<K> previous;
	CinLinkedNode<K> next;
	CinLinkedList<K> list;
	private K _object;

	public CinLinkedNode(K node)
	{
		_object = node;
	}

	public K object()
	{
		return _object;
	}
}
