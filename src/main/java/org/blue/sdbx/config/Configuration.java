package org.blue.sdbx.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangpu
 * Date: 13-4-15
 * Time: 下午5:10
 *
 * XML映射数据库查询列配置 对应 java类
 */
public class Configuration {
    protected String name;
    protected Db db;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public static class Db {
        protected String type;
        protected String sql;
        protected Mapper mapper;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Mapper getMapper() {
            return this.mapper;
        }

        public void setMapper(Mapper mapper) {
            this.mapper = mapper;
        }

        public static class Mapper {
            protected String namespace;
            protected Map<String, Element> elementMap;

            public String getNamespace() {
                return namespace;
            }

            public void setNamespace(String namespace) {
                this.namespace = namespace;
            }

            public Map<String, Element> getElementMap() {
                if (elementMap == null) {
                    elementMap = new HashMap<String, Element>();
                }
                return this.elementMap;
            }

            public void setElementMap(Map<String, Element> element) {
                this.elementMap = element;
            }

            public static class Element {
                protected String name;
                protected String column;
                protected String handler;
                protected Method method;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getColumn() {
                    return column;
                }

                public void setColumn(String column) {
                    this.column = column;
                }

                public String getHandler() {
                    return handler;
                }

                public void setHandler(String handler) {
                    this.handler = handler;
                }

                public Method getMethod() {
                    return method;
                }

                public void setMethod(Method method) {
                    this.method = method;
                }

                public static class Method {
                    protected String name;
                    protected List<Param> params;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public List<Param> getParams() {
                        if (params == null) {
                            params = new ArrayList<Param>();
                        }
                        return this.params;
                    }

                    public void setParams(List<Param> params) {
                        this.params = params;
                    }

                    public static class Param {
                        protected String value;

                        public String getValue() {
                            return value;
                        }

                        public void setValue(String value) {
                            this.value = value;
                        }
                    }
                }
            }
        }
    }
}
