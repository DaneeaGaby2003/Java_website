// src/index.js
import "dotenv/config";
import express from "express";
import morgan from "morgan";
import cors from "cors";
import mongoose from "mongoose";

/* =================== Config =================== */
const PORT = process.env.PORT || 3000;
const API_PREFIX = process.env.API_PREFIX || "/api";
const MONGO_URI = process.env.MONGO_URI || "mongodb://localhost:27017/express_mongo_api";

/* =================== App & Middlewares =================== */
const app = express();
app.use(cors());
app.use(express.json());
app.use(morgan("dev"));

/* =================== Modelo (Mongoose) =================== */
const itemSchema = new mongoose.Schema(
  {
    name: { type: String, required: [true, "name is required"], trim: true },
    price: { type: Number, default: 0 },
    inStock: { type: Boolean, default: true },
    category: { type: String, default: "" }
  },
  { timestamps: true }
);
const Item = mongoose.model("Item", itemSchema);

/* =================== Health =================== */
app.get(`${API_PREFIX}/health`, (req, res) => res.json({ ok: true }));

/* =================== Endpoints CRUD + Search/Filter/Sort/Pagination =================== */

/**
 * GET /items?q=&category=&min=&max=&inStock=&sort=&page=&limit=
 * sort: "price" | "-price" | "createdAt" | "-createdAt" ...
 */
app.get(`${API_PREFIX}/items`, async (req, res, next) => {
  try {
    const {
      q,
      category,
      min,
      max,
      inStock,
      sort = "-createdAt",
      page = 1,
      limit = 10
    } = req.query;

    const filter = {};
    if (q) filter.name = { $regex: q, $options: "i" };
    if (category) filter.category = category;
    if (inStock !== undefined) filter.inStock = String(inStock).toLowerCase() === "true";

    if (min !== undefined || max !== undefined) {
      filter.price = {};
      if (min !== undefined) filter.price.$gte = Number(min);
      if (max !== undefined) filter.price.$lte = Number(max);
    }

    const pageNum = Math.max(1, Number(page));
    const limitNum = Math.max(1, Number(limit));
    const skip = (pageNum - 1) * limitNum;

    const data = await Item.find(filter).sort(sort).skip(skip).limit(limitNum);
    const total = await Item.countDocuments(filter);
    res.json({
      data,
      total,
      page: pageNum,
      pages: Math.ceil(total / limitNum)
    });
  } catch (err) {
    next(err);
  }
});

/** POST /items  { name, price?, inStock?, category? } */
app.post(`${API_PREFIX}/items`, async (req, res, next) => {
  try {
    const { name } = req.body;
    if (!name || !String(name).trim()) {
      return res.status(400).json({ message: "name is required" });
    }
    const created = await Item.create(req.body);
    return res.status(201).json(created);
  } catch (err) {
    next(err);
  }
});

/** GET /items/:id */
app.get(`${API_PREFIX}/items/:id`, async (req, res, next) => {
  try {
    const doc = await Item.findById(req.params.id);
    if (!doc) return res.status(404).json({ message: "Not found" });
    res.json(doc);
  } catch (err) {
    next(err);
  }
});

/** PATCH /items/:id */
app.patch(`${API_PREFIX}/items/:id`, async (req, res, next) => {
  try {
    const out = await Item.findByIdAndUpdate(req.params.id, req.body, { new: true });
    if (!out) return res.status(404).json({ message: "Not found" });
    res.json(out);
  } catch (err) {
    next(err);
  }
});

/** DELETE /items/:id */
app.delete(`${API_PREFIX}/items/:id`, async (req, res, next) => {
  try {
    const out = await Item.findByIdAndDelete(req.params.id);
    if (!out) return res.status(404).json({ message: "Not found" });
    res.status(204).send();
  } catch (err) {
    next(err);
  }
});

/* =================== 404 & Error Handler =================== */
app.use((req, res) => res.status(404).json({ error: "Not found" }));

app.use((err, req, res, _next) => {
  console.error(err);
  if (err.name === "CastError") {
    return res.status(400).json({ message: "Invalid id format" });
  }
  if (err.name === "ValidationError") {
    return res.status(400).json({
      message: "Validation error",
      details: Object.values(err.errors).map(e => e.message)
    });
  }
  res.status(500).json({ message: "Internal Server Error" });
});

/* =================== DB + Server =================== */
mongoose
  .connect(MONGO_URI)
  .then(() => {
    console.log("MongoDB conectado");
    app.listen(PORT, () => {
      console.log(`Servidor corriendo en http://localhost:${PORT}`);
      console.log(`Health: http://localhost:${PORT}${API_PREFIX}/health`);
    });
  })
  .catch((err) => {
    console.error("Error conectando a MongoDB:", err);
    process.exit(1);
  });

