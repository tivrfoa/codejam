#include <bits/stdc++.h>

using namespace std;
typedef long long ll;

#define rep(i, a, b) for(int i = a; i < (b); ++i)
#define all(x) begin(x), end(x)
#define sz(x) (int)(x).size()
typedef long long ll;
typedef pair<int, int> pii;
typedef vector<int> vi;

#pragma once

#include <bits/extc++.h> /// include-line, keep-include
const int MAXN = 110;

int N, M, F, S;
string board[MAXN], gboard[MAXN];
vector <pair <int, int> > bloc, gloc;

ll res;

const ll INF = numeric_limits<ll>::max() / 4;
typedef vector<ll> VL;

struct MCMF {
    int N;
    vector<vi> ed, red;
    vector<VL> cap, flow, cost;
    vi seen;
    VL dist, pi;
    vector<pii> par;

    MCMF(int N) :
        N(N), ed(N), red(N), cap(N, VL(N)), flow(cap), cost(cap),
        seen(N), dist(N), pi(N), par(N) {}

    void addEdge(int from, int to, ll cap, ll cost) {
        this->cap[from][to] = cap;
        this->cost[from][to] = cost;
        ed[from].push_back(to);
        red[to].push_back(from);
    }

    void path(int s) {
        fill(all(seen), 0);
        fill(all(dist), INF);
        dist[s] = 0; ll di;

        __gnu_pbds::priority_queue<pair<ll, int>> q;
        vector<decltype(q)::point_iterator> its(N);
        q.push({0, s});

        auto relax = [&](int i, ll cap, ll cost, int dir) {
            ll val = di - pi[i] + cost;
            if (cap && val < dist[i]) {
                dist[i] = val;
                par[i] = {s, dir};
                if (its[i] == q.end()) its[i] = q.push({-dist[i], i});
                else q.modify(its[i], {-dist[i], i});
            }
        };

        while (!q.empty()) {
            s = q.top().second; q.pop();
            seen[s] = 1; di = dist[s] + pi[s];
            for (int i : ed[s]) if (!seen[i])
                relax(i, cap[s][i] - flow[s][i], cost[s][i], 1);
            for (int i : red[s]) if (!seen[i])
                relax(i, flow[i][s], -cost[i][s], 0);
        }
        rep(i,0,N) pi[i] = min(pi[i] + dist[i], INF);
    }

    pair<ll, ll> maxflow(int s, int t) {
        ll totflow = 0, totcost = 0;
        int xx = 0;
        while (path(s), seen[t]) {
            ll fl = INF;
            for (int p,r,x = t; tie(p,r) = par[x], x != s; x = p)
                fl = min(fl, r ? cap[p][x] - flow[p][x] : flow[x][p]);
            totflow += fl;

            for (int p,r,x = t; tie(p,r) = par[x], x != s; x = p)
                if (r) flow[p][x] += fl;
                else flow[x][p] -= fl;

            ll td = 0;
            rep(i,0,N) rep(j,0,N)
            {
                //if (flow[i][j]) cout << i << " " << j << " " << flow[i][j] << " " << cost[i][j] << "\n";
                td += cost[i][j] * flow[i][j];
            }
            xx++;
            //cout << totflow << " " << td << "\n\n";
            res = min (res, (ll) td * S + ((int) bloc.size() + (int) gloc.size() - 2 * totflow) * F);
        }
        rep(i,0,N) rep(j,0,N) totcost += cost[i][j] * flow[i][j];
        return {totflow, totcost};
    }

    // If some costs can be negative, call this before maxflow:
    void setpi(int s) { // (otherwise, leave this out)
        fill(all(pi), INF); pi[s] = 0;
        int it = N, ch = 1; ll v;
        while (ch-- && it--)
            rep(i,0,N) if (pi[i] != INF)
                for (int to : ed[i]) if (cap[i][to])
                    if ((v = pi[i] + cost[i][to]) < pi[to])
                        pi[to] = v, ch = 1;
        assert(it >= 0); // negative cost cycle
    }
};
/*pair<int, vi> hungarian(const vector<vi> &a) {
    if (a.empty()) return {0, {}};
    int n = sz(a) + 1, m = sz(a[0]) + 1;
    vi u(n), v(m), p(m), ans(n - 1);
    rep(i,1,min(n,m)) {
        p[0] = i;
        int j0 = 0; // add "dummy" worker 0
        vi dist(m, INT_MAX), pre(m, -1);
        vector<bool> done(m + 1);
        do { // dijkstra
            done[j0] = true;
            int i0 = p[j0], j1, delta = INT_MAX;
            rep(j,1,m) if (!done[j]) {
                auto cur = a[i0 - 1][j - 1] - u[i0] - v[j];
                if (cur < dist[j]) dist[j] = cur, pre[j] = j0;
                if (dist[j] < delta) delta = dist[j], j1 = j;
            }
            rep(j,0,m) {
                if (done[j]) u[p[j]] += delta, v[j] -= delta;
                else dist[j] -= delta;
            }
            j0 = j1;
        } while (p[j0]);

        while (j0) { // update alternating path
            int j1 = pre[j0];
            p[j0] = p[j1], j0 = j1;
        }

        ll td = 0;
        rep (j, 1, m) if (p[j])
        {
            cout << j << " " << p[j] << " " << a[p[j]-1][j-1] << "\n";
            td += a[p[j]-1][j-1];
        }
        cout << i << " " << td << "\n";
        res = min (res, (ll) td * S + ((int) bloc.size() + (int) gloc.size() - 2 * i) * F);

    }
    rep(j,1,m) if (p[j]) ans[p[j] - 1] = j - 1;
    return {-v[0], ans}; // min cost
}*/

void gogo()
{
    cin >> N >> M >> F >> S;
    for (int i = 0; i < N; i++)
        cin >> board[i];
    for (int i = 0; i < N; i++)
        cin >> gboard[i];

    bloc.clear();
    gloc.clear();
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
        {
            if (board[i][j] == 'M')
                bloc.push_back (make_pair (i, j));
            if (gboard[i][j] == 'M')
                gloc.push_back (make_pair (i, j));
        }

    res = (bloc.size() + gloc.size()) * F;
    vector <vector <int> > vv;
    for (int i = 0; i < bloc.size(); i++)
    {
        vector <int> nv;
        for (int j = 0; j < gloc.size(); j++)
        {
            int d = abs (bloc[i].first - gloc[j].first) + abs (bloc[i].second - gloc[j].second);
            nv.push_back(d);
        }
        vv.push_back (nv);
    }
    int b = bloc.size();
    int g = gloc.size();

    MCMF mm(b + g + 2);
    for (int i = 0; i < b; i++)
        mm.addEdge(b+g, i, 1, 0);
    for (int i = 0; i < g; i++)
        mm.addEdge(i+b, b+g+1, 1, 0);
    for (int i = 0; i < b; i++)
        for (int j = 0; j < g; j++)
            mm.addEdge(i, j+b, 1, vv[i][j]);
    mm.maxflow(b+g, b+g+1);

    cout << res << "\n";
}

int main()
{
    int T; cin >> T;

    for (int t = 0; t < T; t++)
    {
        cout << "Case #" << t + 1 << ": ";
        gogo();
    }
}