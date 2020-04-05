import math

log = False


def inter(i_q, i_b):

    print(i_b + 1, flush=True)
    a = input()
    i_q += 1
    if a == 'N':
        b = -1
    else:
        b = int(a)
    if (log):
        # print(log_file)
        print(i_b + 1, a, file=log_file)
    return i_q, b


def get_bit_index(n, n_b):
    if n_b & 1:
        i_b = n - 1 - ((n_b - 1) >> 1)
    else:
        i_b = n_b >> 1
    return i_b


def algo(n):
    res = [0] * n
    n_b = 0
    i_q = 1
    i_same_reverse = [-1, -1]
    found_ind = False
    while i_q < 150 and n_b < n:
        if i_q % 10 == 1 and n_b > 0:
            # check and get new res
            compl = swap = compl_swap = same = True
            for ic in i_same_reverse:
                if ic >= 0:
                    i_q, b = inter(i_q, ic)
                    if res[ic] == b:
                        compl = False
                    else:
                        same = False
                    if res[n - 1 - ic] == b:
                        compl_swap = False
                    else:
                        swap = False
            if (log):
                print(same, compl , swap , compl_swap , file=log_file)
                print('before',*res, file=log_file)
            if not same:
                if compl or compl_swap:
                    for k in range(n_b ):
                        i_k = get_bit_index(n, k)
                        res[i_k] = 1 - res[i_k]
                    if (log):
                        print('compl ',*res, file=log_file)
                if found_ind and (swap or compl_swap):
                    for k in range(0, (n_b ), 2):
                        i_k = get_bit_index(n, k)
                        res[i_k], res[n - 1 - i_k] = res[n - 1 - i_k], res[i_k]
                    if (log):
                        print('swap  ',*res, file=log_file)
            if n_b&1 and (swap or compl_swap): n_b-=1

        i_b = get_bit_index(n, n_b)
        # if log: print(n,n_b, i_b, n_b & 1, bool(n_b & 1), n_b - 1, (n_b - 1) >> 1, n - 1 - ((n_b - 1) >> 1))
        i_q, b = inter(i_q, i_b)
        if b == -1: return -1
        res[i_b] = b
        if not found_ind and n_b & 1:
            if res[i_b] == res[n - 1 - i_b]:
                if i_same_reverse[0] == -1:
                    i_same_reverse[0] = n - 1 - i_b
                    if i_same_reverse[1] != -1: found_ind = True
            else:
                if i_same_reverse[1] == -1:
                    i_same_reverse[1] = n - 1 - i_b
                    if i_same_reverse[0] != -1: found_ind = True
        n_b += 1


    return res


def inp():
    if log:
        global log_file
        log_file = open('c:\\my\\log_sol.txt', "w")
        # print(log_file)
    t, n = (int(s) for s in input().split(" "))
    for i in range(1, t + 1):
        res = algo(n)
        if (log):
            print(*res, sep='', flush=True, file=log_file)
        if res == -1: break
        print(*res, sep='', flush=True)
        ok = input()
        if ok == 'N': break
    if log: log_file.close()


if __name__ == '__main__':
    inp()
