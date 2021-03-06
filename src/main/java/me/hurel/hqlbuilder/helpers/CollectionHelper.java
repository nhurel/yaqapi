/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. 
 * If a copy of the MPL was not distributed with this file, 
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * Contributors:
 *     Nathan Hurel - initial API and implementation
 */
package me.hurel.hqlbuilder.helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CollectionHelper<E> implements List<E>, Set<E> {

    private final E object;

    public CollectionHelper(E object) {
	this.object = object;
    }

    public E getObject() {
	return object;
    }

    public int size() {
	throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
	throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
	throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {
	throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
	throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] a) {
	throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
	throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
	throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
	throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> c) {
	throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> c) {
	throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
	throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
	throw new UnsupportedOperationException();
    }

    public void clear() {
	throw new UnsupportedOperationException();
    }

    public E get(int index) {
	throw new UnsupportedOperationException();
    }

    public E set(int index, E element) {
	throw new UnsupportedOperationException();
    }

    public void add(int index, E element) {
	throw new UnsupportedOperationException();
    }

    public E remove(int index) {
	throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
	throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
	throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator() {
	throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator(int index) {
	throw new UnsupportedOperationException();
    }

    public List<E> subList(int fromIndex, int toIndex) {
	throw new UnsupportedOperationException();
    }

}
