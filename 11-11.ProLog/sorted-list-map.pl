map_get([(CurrentKey, CurrentValue) | Tail], CurrentKey, CurrentValue).
map_get([(CurrentKey, CurrentValue) | Tail], Key, Value) :- Key > CurrentKey, map_get(Tail, Key, Value).

map_put([], Key, Value, [(Key, Value)]) :- !.
map_put([(Key, Value) | Tail], Key, NewValue, [(Key, NewValue) | Tail]) :- !.
map_put([(Key, Value) | Tail], NewKey, NewValue, [(Key, Value) | Result]) :-
    Key < NewKey, map_put(Tail, NewKey, NewValue, Result), !.
map_put(ListMap, Key, Value, [(Key, Value) | ListMap]).

map_remove([(Key, _) | Tail], Key, Tail) :- !.
map_remove([(CurrentKey, Value) | Tail], Key, [(CurrentKey, Value) | Result]) :-
    Key > CurrentKey, map_remove(Tail, Key, Result), !.
map_remove(ListMap, _, ListMap).

map_size([], 0).
map_size([(_, _) | Tail], Size) :- map_size(Tail, TailSize), Size is TailSize + 1.
