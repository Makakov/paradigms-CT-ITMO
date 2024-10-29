find_divisor(N, A) :-
    A * A =< N,
    A1 is A + 1,
    (0 is N mod A;
    find_divisor(N, A1)).

prime(2) :- !.

prime(N) :-
    N > 2,
    1 is (N mod 2),
    \+(find_divisor(N, 2)).

composite(N) :-
    N > 2,
    \+prime(N).

fill_list_divisors(N, A, [H | T]) :-
    A =< N,
    prime(A) , 0 is N mod A,
    N1 is N // A,
    H = A,
    fill_list_divisors(N1, A, T).

fill_list_divisors(N, A, NewLIST) :-
    A =< N,
    \+(0 is N mod A),
    A1 is A + 1,
    fill_list_divisors(N, A1, NewLIST).

fill_list_divisors(N, A, []) :-
    N < A,
    !.

prime_divisors(1, []) :-
    !.

prime_divisors(N, Divisors) :-
    fill_list_divisors(N, 2, Divisors), !.

find_nth_prime(CNT, Prime, Prime) :-
    1 is CNT,
    prime(Prime),
    !.

find_nth_prime(CNT, Prime, P) :-
    composite(P),
    P1 is P + 1,
    find_nth_prime(CNT, Prime, P1).

find_nth_prime(CNT, Prime, P) :-
    prime(P),
    CNT1 is CNT - 1,
    P1 is P + 1,
    find_nth_prime(CNT1, Prime, P1).

nth_prime(N, P) :-
    find_nth_prime(N, P, 2).