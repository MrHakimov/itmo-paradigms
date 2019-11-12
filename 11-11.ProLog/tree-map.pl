getRandomConstant(N) :- N is 1000000000.
getRandomValue(N) :-
    getRandomConstant(MAX_VALUE),
    rand_int(MAX_VALUE, N).

split(nil, Key, nil, nil).
split(tree((X, V, Y), L, R), Key, Left, Right) :-
    Key > X,
    split(R, Key, NewR, Right),
    Left = tree((X, V, Y), L, NewR).
split(tree((X, V, Y), L, R), Key, Left, Right) :-
    Key =< X,
    split(L, Key, Left, NewL),
    Right = tree((X, V, Y), NewL, R).

merge(nil, nil, nil).
merge(Tree, nil, Tree).
merge(nil, Tree, Tree).
merge(tree((FirstX, FirstV, FirstY), FirstL, FirstR),
      tree((SecondX, SecondV, SecondY), SecondL, SecondR), Tree) :-
    FirstY > SecondY,
    merge(FirstR, tree((SecondX, SecondV, SecondY), SecondL, SecondR), FirstR),
    Tree = tree((FirstX, FirstV, FirstY), FirstL, FirstR).
merge(tree((FirstX, FirstV, FirstY), FirstL, FirstR),
      tree((SecondX, SecondV, SecondY), SecondL, SecondR), Tree) :-
    FirstY =< SecondY,
    merge(tree((FirstX, FirstV, FirstY), FirstL, FirstR), SecondL, SecondL),
    Tree = tree((SecondX, SecondV, SecondY), SecondL, SecondR).

find(nil, _, _, 0).
find(tree((X, V, _), _, _), X, V, 1).
find(tree((X, _, _), L, _), Key, Value, Result) :-
    Key < X,
    find(L, Key, Value, Result).
find(tree((X, _, _), _, R), Key, Value, Result) :-
    Key > X,
    find(R, Key, Value, Result).
map_get(Tree, X, V) :-
    find(Tree, X, V, Result),
    Result == 1.

insert(nil, (X, V, Y), tree((X, V, Y), nil, nil)).
insert(tree((X, V, Y), L, R), (Key, Value, Prior), tree((Key, Value, Prior), Left, Right)) :-
    Prior > Y,
    split(tree((X, V, Y), L, R), Key, Left, Right).
insert(tree((X, V, Y), L, R), (Key, Value, Prior), tree((X, V, Y), NewTree, R)) :-
    Prior =< Y, Key < X,
    insert(L, (Key, Value, Prior), NewTree).
insert(tree((X, V, Y), L, R), (Key, Value, Prior), tree((X, V, Y), L, NewTree)) :-
    Prior =< Y, Key >= X,
    insert(R, (Key, Value, Prior), NewTree).

replace(nil, X, V, nil).
replace(tree((X, _, Y), L, R), X, Value, tree((X, Value, Y), L, R)).
replace(tree((X, V, Y), L, R), Key, Value, tree((X, V, Y), Left, R)) :-
    Key < X,
    replace(L, Key, Value, Left).
replace(tree((X, V, Y), L, R), Key, Value, tree((X, V, Y), L, Right)) :-
    Key > K,
    replace(R, Key, Value, Right).

map_put(Tree, X, V, Result) :-
    find(Tree, X, _, Finded),
    (Finded == 1 -> replace(Tree, X, V, Result);
     getRandomValue(RandomY),
     insert(Tree, (X, V, RandomY), Result)).

map_size(nil, 0).
map_size(tree((X, V, Y), L, R), Size) :-
    map_size(L, LeftSize),
    map_size(R, RightSize),
    Size is LeftSize + RightSize + 1.

tree_build([], nil).
tree_build([(X, V) | Rest], Tree) :- tree_build(Rest, NewTree), map_put(NewTree, X, V, Tree).
